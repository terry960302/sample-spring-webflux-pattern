package com.ritier.springr2dbcsample.image.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.image.domain.vo.UploadSummary

data class MultiImageUploadResponse(
    val summary: UploadSummary,
    val results: List<ImageUploadResultDto>
)