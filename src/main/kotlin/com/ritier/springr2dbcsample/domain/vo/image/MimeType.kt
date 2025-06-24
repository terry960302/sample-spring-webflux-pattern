package com.ritier.springr2dbcsample.domain.vo.image

import com.ritier.springr2dbcsample.common.exception.ErrorCode

@JvmInline
value class MimeType(val value: String) {
    companion object {
        private val SUPPORTED_TYPES = setOf(
            "image/jpeg", "image/jpg", "image/png",
            "image/gif", "image/webp", "image/bmp"
        )
    }

    init {
        require(value.isNotBlank()) { ErrorCode.INVALID_MIME_TYPE }
        require(isImageType()) { ErrorCode.UNSUPPORTED_FILE_TYPE }
    }

    fun isImageType(): Boolean = SUPPORTED_TYPES.contains(value.lowercase())

    fun extension(): String = when (value.lowercase()) {
        "image/jpeg", "image/jpg" -> "jpg"
        "image/png" -> "png"
        "image/gif" -> "gif"
        "image/webp" -> "webp"
        "image/bmp" -> "bmp"
        else -> "unknown"
    }
}