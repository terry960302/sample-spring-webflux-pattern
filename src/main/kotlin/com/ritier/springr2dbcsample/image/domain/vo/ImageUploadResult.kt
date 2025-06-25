package com.ritier.springr2dbcsample.image.domain.vo

import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageDto

sealed class ImageUploadResult {
    data class Success(val image: ImageDto) : ImageUploadResult()
    data class Failure(val error: String) : ImageUploadResult()
}