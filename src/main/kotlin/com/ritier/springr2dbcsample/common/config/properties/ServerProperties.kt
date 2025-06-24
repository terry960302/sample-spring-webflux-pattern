package com.ritier.springr2dbcsample.common.config.properties

import com.ritier.springr2dbcsample.common.exception.ErrorCode
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.annotation.PostConstruct

@ConfigurationProperties(prefix = "server")
@ConstructorBinding
data class ServerProperties(
    val host: String = "localhost",
    val port: Int = 8080,
    val ssl: SslProperties = SslProperties(),
    val performance: PerformanceProperties = PerformanceProperties(),
    val timeout: TimeoutProperties = TimeoutProperties()
) {
    @PostConstruct
    fun validate() {
        require(host.isNotBlank()) { ErrorCode.SERVER_CONFIG_HOST_EMPTY.message }
        require(port in 1..65535) { ErrorCode.SERVER_CONFIG_PORT_INVALID.message }
        require(isValidHost(host)) { ErrorCode.SERVER_CONFIG_HOST_INVALID.message }
    }

    private fun isValidHost(host: String): Boolean {
        return when {
            host == "localhost" -> true
            host.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$".toRegex()) -> {
                // IP 주소 검증
                host.split(".").all { it.toIntOrNull()?.let { num -> num in 0..255 } ?: false }
            }
            else -> host.matches("^[a-zA-Z0-9.-]+$".toRegex()) // 도메인명 검증
        }
    }

    data class SslProperties(
        val enabled: Boolean = false,
        val keyStore: String = "",
        val keyStorePassword: String = ""
    )

    data class PerformanceProperties(
        val maxConnections: Int = 1000,
        val workerThreads: Int = Runtime.getRuntime().availableProcessors() * 2,
        val backlogSize: Int = 128
    ) {
        init {
            require(maxConnections > 0) { ErrorCode.SERVER_CONFIG_THREAD_POOL_INVALID.message }
            require(workerThreads > 0) { ErrorCode.SERVER_CONFIG_THREAD_POOL_INVALID.message }
        }
    }

    data class TimeoutProperties(
        val connectionTimeoutMs: Long = 30000,
        val requestTimeoutMs: Long = 10000,
        val keepAliveTimeoutMs: Long = 60000
    )
}