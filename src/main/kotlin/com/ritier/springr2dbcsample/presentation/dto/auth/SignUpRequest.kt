package com.ritier.springr2dbcsample.presentation.dto.auth

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
    val age: Int,
) {
}