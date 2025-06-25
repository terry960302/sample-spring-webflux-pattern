package com.ritier.springr2dbcsample.image.domain.vo

data class MultiImageUploadResults(
    val results: List<SingleImageUploadResult>
) {
    val successResults: List<SingleImageUploadResult.Success> =
        results.filterIsInstance<SingleImageUploadResult.Success>()

    val failureResults: List<SingleImageUploadResult.Failure> =
        results.filterIsInstance<SingleImageUploadResult.Failure>()

    val successCount: Int = successResults.size
    val failureCount: Int = failureResults.size
    val totalCount: Int = results.size

    val isAllSuccess: Boolean = failureCount == 0
    val hasAnySuccess: Boolean = successCount > 0
}