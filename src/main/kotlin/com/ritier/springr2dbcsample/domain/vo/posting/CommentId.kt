package com.ritier.springr2dbcsample.domain.vo.posting

import com.ritier.springr2dbcsample.common.exception.ErrorCode

@JvmInline
value class CommentId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.COMMENT_ID_INVALID.message }
    }
}