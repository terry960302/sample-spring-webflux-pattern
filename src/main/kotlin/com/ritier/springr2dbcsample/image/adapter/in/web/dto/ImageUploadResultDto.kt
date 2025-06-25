package com.ritier.springr2dbcsample.image.adapter.`in`.web.dto

data class ImageUploadResultDto(
    val fileName: String,
    val status: String, // "SUCCESS" or "FAILURE"
    val url: String? = null,
    val error: String? = null,
    val imageInfo: ImageDto? = null
)