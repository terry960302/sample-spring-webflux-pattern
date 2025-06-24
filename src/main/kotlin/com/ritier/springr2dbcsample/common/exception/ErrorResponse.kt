package com.ritier.springr2dbcsample.common.exception

import java.time.Instant


data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String,
    val timestamp: Instant
) {
    companion object {
        fun from(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(errorCode.status, errorCode.code, errorCode.message, Instant.now())
        }
    }
}