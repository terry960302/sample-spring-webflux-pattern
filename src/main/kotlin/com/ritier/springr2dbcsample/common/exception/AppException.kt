package com.ritier.springr2dbcsample.common.exception

import java.time.Instant

class AppException(
    val errorCode: ErrorCode,
    throwable: Throwable? = null
) : RuntimeException(errorCode.message, throwable) {

    fun toErrorResponse(): ErrorResponse {
        return ErrorResponse(
            status = errorCode.status,
            code = errorCode.code,
            message = errorCode.message,
            timestamp = Instant.now(),
        )
    }
}