package com.ritier.springr2dbcsample.domain.vo.user

import com.ritier.springr2dbcsample.common.exception.ErrorCode

@JvmInline
value class UserCredentialId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.COMMENT_ID_INVALID.message }
    }
}