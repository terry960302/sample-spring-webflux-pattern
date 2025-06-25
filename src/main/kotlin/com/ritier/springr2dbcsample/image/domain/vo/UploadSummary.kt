package com.ritier.springr2dbcsample.image.domain.vo

data class UploadSummary(
    val totalFiles: Int,
    val successCount: Int,
    val failureCount: Int,
    val isAllSuccess: Boolean
)