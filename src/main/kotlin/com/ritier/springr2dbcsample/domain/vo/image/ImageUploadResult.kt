package com.ritier.springr2dbcsample.domain.vo.image

import com.ritier.springr2dbcsample.presentation.dto.image.ImageDto

sealed class ImageUploadResult {
    data class Success(val image: ImageDto) : ImageUploadResult()
    data class Failure(val error: String) : ImageUploadResult()
}