package com.ritier.springr2dbcsample.auth.domain.port.input

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.auth.application.command.AuthenticateUserCommand
import com.ritier.springr2dbcsample.auth.domain.port.output.PasswordHasher
import com.ritier.springr2dbcsample.user.domain.port.output.UserCredentialRepository
import com.ritier.springr2dbcsample.auth.domain.vo.LoginResult
import com.ritier.springr2dbcsample.auth.adapter.out.security.JwtTokenProvider
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class UserAuthenticationUseCase(
    private val userCredentialRepository: UserCredentialRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenProvider: JwtTokenProvider
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: AuthenticateUserCommand): String {
        // 기존 AuthService의 credential 조회 로직
        val credential = userCredentialRepository.findByEmail(command.email)
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        // 기존 attemptLogin 로직 활용
        val updatedCredential = when (val loginResult = credential.attemptLogin(command.password, passwordHasher)) {
            is LoginResult.Success -> {
                userCredentialRepository.updateLastLogin(credential.userId)
                loginResult.credential
            }
            LoginResult.AccountInactive -> throw AppException(ErrorCode.USER_INACTIVE)
            LoginResult.WrongPassword -> throw AppException(ErrorCode.INVALID_PASSWORD)
        }

        // 기존 토큰 발급 로직 활용
        val token = updatedCredential.issueToken(tokenProvider)
        logger.info { "로그인 UseCase 실행 완료: ${command.email}" }

        return token
    }
}