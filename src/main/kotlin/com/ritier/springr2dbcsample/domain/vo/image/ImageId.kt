package com.ritier.springr2dbcsample.domain.vo.image

import com.ritier.springr2dbcsample.common.exception.ErrorCode

@JvmInline
value class ImageId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.IMAGE_ID_INVALID.message }
    }
}