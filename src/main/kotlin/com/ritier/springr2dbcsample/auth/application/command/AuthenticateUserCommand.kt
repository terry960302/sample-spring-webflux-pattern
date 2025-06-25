package com.ritier.springr2dbcsample.auth.application.command

data class AuthenticateUserCommand(
    val email: String,
    val password: String
)