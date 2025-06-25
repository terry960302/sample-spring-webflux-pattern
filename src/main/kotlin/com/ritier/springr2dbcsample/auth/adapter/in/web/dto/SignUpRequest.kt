package com.ritier.springr2dbcsample.auth.adapter.`in`.web.dto

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
    val age: Int,
) {
}