package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.application.factory.ImageFactory
import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.domain.repository.ImageRepository
import com.ritier.springr2dbcsample.domain.vo.image.ImageUploadResult
import com.ritier.springr2dbcsample.domain.vo.image.StorageResult
import com.ritier.springr2dbcsample.infrastructure.storage.port.ImageStoragePort
import mu.KotlinLogging
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service


@Service
class ImageUploadService(
    private val imageStoragePort: ImageStoragePort,
    private val imageRepository: ImageRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun uploadImage(filePart: FilePart): ImageUploadResult {
        return try {
            val (image, payload) = ImageFactory.fromFilePart(filePart)

            logImageInfo(image)

            val storageResult = imageStoragePort.upload(image, payload)

            when (storageResult) {
                is StorageResult.Success -> {
                    val imageWithUrl = image.copy(url = storageResult.url)
                    val savedImage = imageRepository.save(imageWithUrl)

                    logger.info { "이미지 업로드 성공: ${savedImage.fileName.value}" }
                    ImageUploadResult.Success(savedImage.toDto())
                }
                is StorageResult.Failure -> {
                    logger.error { "스토리지 업로드 실패: ${storageResult.error}" }
                    ImageUploadResult.Failure(storageResult.error)
                }
            }
        } catch (e: AppException) {
            logger.error(e) { "이미지 업로드 실패: ${e.message}" }
            ImageUploadResult.Failure(e.message ?: "알 수 없는 오류")
        }
    }

    private fun logImageInfo(image: Image) {
        if (!image.isWebOptimized()) {
            logger.warn { "웹 최적화되지 않은 이미지: ${image.fileName.value}" }
        }
        if (image.needsOptimization()) {
            logger.info { "최적화 권장 이미지: ${image.fileName.value}" }
        }
    }
}