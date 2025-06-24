package com.ritier.springr2dbcsample.domain.vo.image

import com.ritier.springr2dbcsample.common.exception.ErrorCode

@JvmInline
value class ImageFileName(val value: String) {
    init {
        require(value.isNotBlank()) { ErrorCode.INVALID_FILENAME.message }
        require(value.length <= MAX_LENGTH) { ErrorCode.FILENAME_TOO_LONG.message }
        require(value.none { it in INVALID_CHARS }) { ErrorCode.INVALID_FILENAME_CHARS.message }
    }

    companion object {
        private val INVALID_CHARS = setOf('/', '\\', '?', '%', '*', ':', '|', '"', '<', '>', '.', ' ')
        private const val MAX_LENGTH = 255
    }

    fun sanitized(): ImageFileName {
        val sanitized = value
            .replace(Regex("[^a-zA-Z0-9._-]"), "_")
            .take(MAX_LENGTH)
        return ImageFileName(sanitized)
    }

    fun extension(): String = value.substringAfterLast('.', "")
}