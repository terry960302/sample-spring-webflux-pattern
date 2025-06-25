package com.ritier.springr2dbcsample.image.domain.vo

import com.ritier.springr2dbcsample.shared.exception.ErrorCode

@JvmInline
value class ImageId(val value: Long) {
    init {
        require(value >= 0) { ErrorCode.IMAGE_ID_INVALID.message }
    }
}