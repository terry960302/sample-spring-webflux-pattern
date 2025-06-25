package com.ritier.springr2dbcsample.auth.application.command

data class RegisterUserCommand(
    val username: String,
    val email: String,
    val age: Int,
    val password: String

) {
}