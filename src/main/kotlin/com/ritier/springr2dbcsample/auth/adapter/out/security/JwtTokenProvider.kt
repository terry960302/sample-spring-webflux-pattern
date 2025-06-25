package com.ritier.springr2dbcsample.auth.adapter.out.security

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.auth.domain.port.output.TokenProvider
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import org.springframework.stereotype.Component

@Component
class JwtTokenProvider : TokenProvider {
    override fun generateToken(userId: UserId): String {
        return try {
            // TODO: JWT 토큰 생성 로직
            "jwt-token-for-${userId.value}"
        } catch (e: Exception) {
            throw AppException(ErrorCode.TOKEN_GENERATION_FAILED, e)
        }
    }

    override fun validateToken(token: String): UserId? {
        return try {
            // JWT 토큰 검증 로직
            val userId = extractUserIdFromToken(token)
            UserId(userId)
        } catch (e: Exception) {
            null
        }
    }

    private fun extractUserIdFromToken(token: String): Long {
        // 실제 JWT 파싱 로직
        return token.substringAfterLast("-").toLong()
    }
}