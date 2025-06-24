package com.ritier.springr2dbcsample.domain.vo.posting

import com.ritier.springr2dbcsample.common.exception.ErrorCode

@JvmInline
value class PostingId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.POSTING_ID_INVALID.message }
    }
}