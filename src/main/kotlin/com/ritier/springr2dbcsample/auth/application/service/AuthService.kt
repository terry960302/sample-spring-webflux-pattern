package com.ritier.springr2dbcsample.auth.application.service

import com.ritier.springr2dbcsample.auth.application.command.AuthenticateUserCommand
import com.ritier.springr2dbcsample.auth.application.command.RegisterUserCommand
import com.ritier.springr2dbcsample.auth.domain.port.input.UserAuthenticationUseCase
import com.ritier.springr2dbcsample.auth.domain.port.input.UserRegistrationUseCase
import com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto.TokenResponse
import com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto.SignInRequest
import com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto.SignUpRequest
import com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto.SignUpResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRegistrationUseCase: UserRegistrationUseCase,
    private val userAuthenticationUseCase: UserAuthenticationUseCase
) {
    private val logger = KotlinLogging.logger {}

    suspend fun signUp(request: SignUpRequest): SignUpResponse {
        val command = RegisterUserCommand(
            username = request.username,
            email = request.email,
            age = request.age,
            password = request.password
        )

        val savedUser = userRegistrationUseCase.execute(command)
        logger.info { "회원가입 완료: ${savedUser.email.value}" }

        return SignUpResponse(
            savedUser.id.value,
            savedUser.email.value,
            savedUser.username
        )
    }

    suspend fun signIn(request: SignInRequest): TokenResponse {
        val command = AuthenticateUserCommand(
            email = request.email,
            password = request.password
        )

        val token = userAuthenticationUseCase.execute(command)
        logger.info { "로그인 완료: ${request.email}" }

        return TokenResponse(token)
    }
}