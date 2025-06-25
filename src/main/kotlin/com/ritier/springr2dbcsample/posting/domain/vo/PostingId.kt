package com.ritier.springr2dbcsample.posting.domain.vo

import com.ritier.springr2dbcsample.shared.exception.ErrorCode

@JvmInline
value class PostingId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.POSTING_ID_INVALID.message }
    }
}