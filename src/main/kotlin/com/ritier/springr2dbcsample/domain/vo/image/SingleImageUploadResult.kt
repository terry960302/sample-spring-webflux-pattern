package com.ritier.springr2dbcsample.domain.vo.image

import com.ritier.springr2dbcsample.presentation.dto.image.ImageDto

sealed class SingleImageUploadResult {
    abstract val fileName: String

    data class Success(
        override val fileName: String,
        val url: String,
        val imageDto: ImageDto
    ) : SingleImageUploadResult()

    data class Failure(
        override val fileName: String,
        val error: String
    ) : SingleImageUploadResult()
}