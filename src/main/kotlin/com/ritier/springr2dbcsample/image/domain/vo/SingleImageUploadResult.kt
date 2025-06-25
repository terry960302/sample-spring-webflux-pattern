package com.ritier.springr2dbcsample.image.domain.vo

import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageDto

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