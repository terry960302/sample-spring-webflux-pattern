package com.ritier.springr2dbcsample.domain.helper

interface PasswordHasher {
    fun hash(rawPassword: String): String
    fun matches(rawPassword: String, hashedPassword: String): Boolean
}