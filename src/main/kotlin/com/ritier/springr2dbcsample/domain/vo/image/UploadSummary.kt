package com.ritier.springr2dbcsample.domain.vo.image

data class UploadSummary(
    val totalFiles: Int,
    val successCount: Int,
    val failureCount: Int,
    val isAllSuccess: Boolean
)