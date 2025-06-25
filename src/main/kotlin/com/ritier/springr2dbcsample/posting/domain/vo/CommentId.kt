package com.ritier.springr2dbcsample.posting.domain.vo

import com.ritier.springr2dbcsample.shared.exception.ErrorCode

@JvmInline
value class CommentId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.COMMENT_ID_INVALID.message }
    }
}