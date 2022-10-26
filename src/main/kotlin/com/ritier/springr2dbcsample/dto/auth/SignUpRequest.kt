package com.ritier.springr2dbcsample.dto.auth

class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val age: Int,
) {
}