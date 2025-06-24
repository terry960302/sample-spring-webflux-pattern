package com.ritier.springr2dbcsample.presentation.mapper

import com.ritier.springr2dbcsample.domain.vo.image.MultiImageUploadResults
import com.ritier.springr2dbcsample.domain.vo.image.SingleImageUploadResult
import com.ritier.springr2dbcsample.domain.vo.image.UploadSummary
import com.ritier.springr2dbcsample.presentation.dto.image.ImageUploadResultDto
import com.ritier.springr2dbcsample.presentation.dto.image.MultiImageUploadResponse
import org.springframework.stereotype.Component

@Component
class ImageUploadResponseMapper {
    fun mapToResponse(results: MultiImageUploadResults): MultiImageUploadResponse {
        return MultiImageUploadResponse(
            summary = UploadSummary(
                totalFiles = results.totalCount,
                successCount = results.successCount,
                failureCount = results.failureCount,
                isAllSuccess = results.isAllSuccess
            ),
            results = results.results.map { result ->
                when (result) {
                    is SingleImageUploadResult.Success -> ImageUploadResultDto(
                        fileName = result.fileName,
                        status = "SUCCESS",
                        url = result.url,
                        imageInfo = result.imageDto
                    )
                    is SingleImageUploadResult.Failure -> ImageUploadResultDto(
                        fileName = result.fileName,
                        status = "FAILURE",
                        error = result.error
                    )
                }
            }
        )
    }
}
