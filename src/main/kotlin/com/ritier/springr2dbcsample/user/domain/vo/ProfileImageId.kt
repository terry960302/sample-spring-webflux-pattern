package com.ritier.springr2dbcsample.user.domain.vo

import com.ritier.springr2dbcsample.shared.exception.ErrorCode

@JvmInline
value class ProfileImageId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.POSTING_ID_INVALID.message }
    }
}