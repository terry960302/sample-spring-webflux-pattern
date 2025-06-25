package com.ritier.springr2dbcsample.image.adapter.`in`.web

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.MultiImageUploadRequest
import com.ritier.springr2dbcsample.image.adapter.`in`.web.mapper.ImageUploadResponseMapper
import com.ritier.springr2dbcsample.image.application.service.ImageUploadService
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class ImageHandler(
    private val imageUploadService: ImageUploadService,
    private val responseMapper: ImageUploadResponseMapper
) {

    private val logger = KotlinLogging.logger {}

    suspend fun uploadImages(serverRequest: ServerRequest): ServerResponse {
        val uploadRequest = extract(serverRequest)
        val uploadResults = imageUploadService.uploadMultipleImages(uploadRequest)
        val response = responseMapper.mapToResponse(uploadResults)

        logger.info { "이미지 업로드 처리 완료: ${uploadResults.successCount}개 성공, ${uploadResults.failureCount}개 실패" }

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValueAndAwait(response)
    }

    suspend fun extract(serverRequest: ServerRequest): MultiImageUploadRequest {
        val files: List<FilePart> = serverRequest.multipartData()
            .map { multipartData ->
                multipartData["files"] as? List<FilePart>
                    ?: throw AppException(ErrorCode.FILES_NOT_FOUND)
            }
            .awaitSingle()

        if (files.isEmpty()) {
            throw AppException(ErrorCode.NO_FILES_PROVIDED)
        }

        return MultiImageUploadRequest(files)
    }
}