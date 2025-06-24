package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.helper.PasswordHasher
import com.ritier.springr2dbcsample.domain.model.User
import com.ritier.springr2dbcsample.domain.repository.UserCredentialRepository
import com.ritier.springr2dbcsample.domain.repository.UserRepository
import com.ritier.springr2dbcsample.domain.vo.user.Email
import com.ritier.springr2dbcsample.domain.vo.auth.LoginResult
import com.ritier.springr2dbcsample.infrastructure.security.JwtTokenProvider
import com.ritier.springr2dbcsample.presentation.dto.auth.TokenResponse
import com.ritier.springr2dbcsample.presentation.dto.auth.SignInRequest
import com.ritier.springr2dbcsample.presentation.dto.auth.SignUpRequest
import com.ritier.springr2dbcsample.presentation.dto.auth.SignUpResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userCredentialRepository: UserCredentialRepository,
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenProvider: JwtTokenProvider,
) {
    private val logger = KotlinLogging.logger {}

    suspend fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw AppException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = User.create(request.username, Email(request.email), request.age)
        val savedUser = userRepository.save(user)

        val credential = savedUser.createCredential(request.password, passwordHasher)
        userCredentialRepository.save(credential)

        logger.info { "회원가입 성공: ${savedUser.email.value}" }

        return SignUpResponse(user.id.value, user.email.value, user.username);
    }

    suspend fun signIn(request: SignInRequest): TokenResponse {
        val credential = userCredentialRepository.findByEmail(request.email)
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)


        val updatedCredential = when (val loginResult = credential.attemptLogin(request.password, passwordHasher)) {
            is LoginResult.Success -> {
                userCredentialRepository.updateLastLogin(credential.userId)
                loginResult.credential
            }
            LoginResult.AccountInactive -> throw AppException(ErrorCode.USER_INACTIVE)
            LoginResult.WrongPassword -> throw AppException(ErrorCode.INVALID_PASSWORD)
        }

        val token = updatedCredential.issueToken(tokenProvider);

        logger.info { "로그인 성공: ${request.email}" }
        return TokenResponse(token)

    }

}