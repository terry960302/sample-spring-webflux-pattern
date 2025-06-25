package com.ritier.springr2dbcsample.image.adapter.`in`.web.mapper

import com.ritier.springr2dbcsample.image.domain.vo.MultiImageUploadResults
import com.ritier.springr2dbcsample.image.domain.vo.SingleImageUploadResult
import com.ritier.springr2dbcsample.image.domain.vo.UploadSummary
import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageUploadResultDto
import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.MultiImageUploadResponse
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
