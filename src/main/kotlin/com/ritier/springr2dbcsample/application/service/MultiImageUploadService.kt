package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.vo.image.ImageUploadResult
import com.ritier.springr2dbcsample.domain.vo.image.MultiImageUploadResults
import com.ritier.springr2dbcsample.domain.vo.image.SingleImageUploadResult
import com.ritier.springr2dbcsample.presentation.dto.image.MultiImageUploadRequest
import mu.KotlinLogging
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service

@Service
class MultiImageUploadService(
    private val imageUploadService: ImageUploadService,
    private val uploadExecutor: ImageUploadExecutor
) {
    private val logger = KotlinLogging.logger {}

    suspend fun uploadMultipleImages(request: MultiImageUploadRequest): MultiImageUploadResults {
        logger.info { "다중 이미지 업로드 시작: ${request.files.size}개 파일" }

        return uploadExecutor.executeParallel(request.files) { filePart ->
            uploadSingleImage(filePart)
        }
    }

    private suspend fun uploadSingleImage(filePart: FilePart): SingleImageUploadResult {
        return try {
            val uploadResult = imageUploadService.uploadImage(filePart)
            when (uploadResult) {
                is ImageUploadResult.Success ->
                    SingleImageUploadResult.Success(
                        fileName = filePart.filename() ?: "unknown",
                        url = uploadResult.image.url ?: "",
                        imageDto = uploadResult.image
                    )
                is ImageUploadResult.Failure ->
                    SingleImageUploadResult.Failure(
                        fileName = filePart.filename() ?: "unknown",
                        error = uploadResult.error
                    )
            }
        } catch (e: Exception) {
            logger.warn(e) { "개별 이미지 업로드 실패: ${filePart.filename()}" }
            SingleImageUploadResult.Failure(
                fileName = filePart.filename() ?: "unknown",
                error = e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message,
            )
        }
    }
}
