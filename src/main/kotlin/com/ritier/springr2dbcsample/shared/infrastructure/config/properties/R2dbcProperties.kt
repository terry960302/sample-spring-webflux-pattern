package com.ritier.springr2dbcsample.shared.infrastructure.config.properties

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.annotation.PostConstruct

@ConfigurationProperties(prefix = "spring.r2dbc")
@ConstructorBinding
data class R2dbcProperties(
    val driver: String = "postgresql",
    val host: String,
    val protocol: String,
    val database: String,
    val port: Int,
    val username: String,
    val password: String,
    val pool: PoolProperties = PoolProperties()
) {

    companion object {
        private val DRIVERS: List<String> = listOf("postgresql", "mysql", "h2", "oracle");
        private val PROTOCOLS: List<String> = listOf("r2dbc:postgresql", "r2dbc:mysql", "h2", "r2dbc:oracle")
    }

    @PostConstruct
    fun validate() {
        when {
            host.isBlank() -> throw AppException(ErrorCode.DB_CONFIG_HOST_EMPTY)
            database.isBlank() -> throw AppException(ErrorCode.DB_CONFIG_DATABASE_EMPTY)
            username.isBlank() -> throw AppException(ErrorCode.DB_CONFIG_USERNAME_EMPTY)
            port !in 1..65535 -> throw AppException(ErrorCode.DB_CONFIG_PORT_INVALID)
            password.isBlank() -> throw AppException(ErrorCode.DB_CONFIG_PASSWORD_EMPTY)
            !isValidDriver(driver) -> throw AppException(ErrorCode.DB_CONFIG_DRIVER_INVALID)
            !isValidProtocol(protocol) -> throw AppException(ErrorCode.DB_CONFIG_PROTOCOL_INVALID)
        }
    }

    private fun isValidDriver(driver: String): Boolean {
        return driver in DRIVERS
    }

    private fun isValidProtocol(protocol: String): Boolean {
        return protocol in PROTOCOLS
    }

    // 커넥션 풀 설정
    data class PoolProperties(
        val enabled: Boolean = true,
        val initialSize: Int = 10,
        val maxSize: Int = 20,
        val maxIdleTime: String = "30m"
    )
}