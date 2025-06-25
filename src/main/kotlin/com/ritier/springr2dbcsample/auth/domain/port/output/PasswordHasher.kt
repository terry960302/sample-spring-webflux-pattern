package com.ritier.springr2dbcsample.auth.domain.port.output

interface PasswordHasher {
    fun hash(rawPassword: String): String
    fun matches(rawPassword: String, hashedPassword: String): Boolean
}