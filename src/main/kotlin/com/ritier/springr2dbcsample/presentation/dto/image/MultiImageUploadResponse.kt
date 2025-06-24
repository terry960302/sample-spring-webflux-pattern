package com.ritier.springr2dbcsample.presentation.dto.image

import com.ritier.springr2dbcsample.domain.vo.image.UploadSummary

data class MultiImageUploadResponse(
    val summary: UploadSummary,
    val results: List<ImageUploadResultDto>
)