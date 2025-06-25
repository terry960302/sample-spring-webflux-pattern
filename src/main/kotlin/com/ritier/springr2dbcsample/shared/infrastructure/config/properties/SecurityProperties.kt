package com.ritier.springr2dbcsample.shared.infrastructure.config.properties

import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "app.security")
@ConstructorBinding
data class SecurityProperties(
    val jwt: JwtProperties,
    val password: PasswordProperties,
) {

    data class JwtProperties(
        val secret: String,
        val expiration: Long,
    ) {
        init {
            require(secret.length >= 64) {
                ErrorCode.INVALID_JWT_SECRET_KEY.message
            }
            require(expiration > 0) {
                ErrorCode.INVALID_JWT_EXPIRATION.message
            }
        }
    }

    data class PasswordProperties(
        val hash: HashProperties,
    ) {
        data class HashProperties(
            val rounds: Int = 12,
        ) {
            init {
                require(rounds in 4..31) {
                    ErrorCode.INVALID_PASSWORD_HASH_ROUNDS.message
                }
            }
        }
    }
}
