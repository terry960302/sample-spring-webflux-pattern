package com.ritier.springr2dbcsample.domain.vo.posting

import com.ritier.springr2dbcsample.common.exception.ErrorCode

data class PostingContent(val value: String) {
    companion object{
        private const val MAX_LENGTH = 2000;
    }


    init {
        require(value.isNotBlank()) { ErrorCode.POSTING_CONTENT_EMPTY.message }
        require(value.length <= MAX_LENGTH) { ErrorCode.POSTING_CONTENT_TOO_LONG.message }
    }

    fun getPreview(length: Int = 100): String {
        return if (value.length <= length) value
        else value.take(length) + "..."
    }
}