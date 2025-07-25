package com.ritier.springr2dbcsample.auth.adapter.`in`.web

import com.ritier.springr2dbcsample.auth.application.service.AuthService
import com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto.SignInRequest
import com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto.SignUpRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AuthHandler(private val authService: AuthService) {

    suspend fun signUp(request: ServerRequest): ServerResponse {
        val signUpRequest = request.awaitBody<SignUpRequest>()
        val signUpResponse = authService.signUp(signUpRequest)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(signUpResponse)
    }

    suspend fun signIn(request: ServerRequest): ServerResponse {
        val signInRequest = request.awaitBody<SignInRequest>()
        val tokenResponse = authService.signIn(signInRequest)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(tokenResponse)
    }
}