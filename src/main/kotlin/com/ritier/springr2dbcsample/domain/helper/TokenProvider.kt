package com.ritier.springr2dbcsample.domain.helper

import com.ritier.springr2dbcsample.domain.vo.user.UserId

interface TokenProvider {
    fun generateToken(userId: UserId): String
    fun validateToken(token: String): UserId?
}
