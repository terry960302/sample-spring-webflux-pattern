package com.ritier.springr2dbcsample.domain.vo.user

import com.ritier.springr2dbcsample.common.exception.ErrorCode

data class Email(val value: String) {
    init {
        require(value.isNotBlank()) { ErrorCode.EMAIL_EMPTY.message }
        require(EMAIL_PATTERN.matches(value)) { ErrorCode.EMAIL_INVALID_FORMAT.message }
    }

    companion object {
        private val EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    }
}