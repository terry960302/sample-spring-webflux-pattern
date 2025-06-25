package com.ritier.springr2dbcsample.image.application.service

import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.MultiImageUploadRequest
import com.ritier.springr2dbcsample.image.domain.port.input.MultiImageUploadUseCase
import com.ritier.springr2dbcsample.image.domain.port.input.SingleImageUploadUseCase
import com.ritier.springr2dbcsample.image.domain.vo.ImageUploadResult
import com.ritier.springr2dbcsample.image.domain.vo.MultiImageUploadResults
import mu.KotlinLogging
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class ImageUploadService(
    private val singleImageUploadUseCase: SingleImageUploadUseCase,
    private val multiImageUploadUseCase: MultiImageUploadUseCase
) {
    private val logger = KotlinLogging.logger {}

    suspend fun uploadImage(filePart: FilePart): ImageUploadResult {
        val result = singleImageUploadUseCase.execute(filePart)
        logger.info { "단일 이미지 업로드 완료: ${filePart.filename()}" }
        return result
    }

    suspend fun uploadMultipleImages(request: MultiImageUploadRequest): MultiImageUploadResults {
        val results = multiImageUploadUseCase.execute(request.files)
        logger.info { "다중 이미지 업로드 완료: ${request.files.size}개 파일" }
        return results
    }
}