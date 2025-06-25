package com.ritier.springr2dbcsample.auth.domain.port.output

import com.ritier.springr2dbcsample.user.domain.vo.UserId

interface TokenProvider {
    fun generateToken(userId: UserId): String
    fun validateToken(token: String): UserId?
}
