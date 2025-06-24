package com.ritier.springr2dbcsample.presentation.dto.image

import com.ritier.springr2dbcsample.common.exception.ErrorCode
import org.springframework.http.codec.multipart.FilePart

data class MultiImageUploadRequest(
    val files: List<FilePart>
) {
    init {
        require(files.isNotEmpty()) { ErrorCode.NO_FILES_PROVIDED }
        require(files.size <= MAX_FILES) { ErrorCode.UPLOAD_FILES_SIZE_EXCEEDED }
    }

    companion object {
        private const val MAX_FILES = 10
    }
}