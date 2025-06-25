package com.ritier.springr2dbcsample.image.domain.port.input

import com.ritier.springr2dbcsample.image.domain.vo.ImageUploadResult
import com.ritier.springr2dbcsample.image.domain.vo.MultiImageUploadResults
import com.ritier.springr2dbcsample.image.domain.vo.SingleImageUploadResult
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import mu.KotlinLogging
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component

@Component
class MultiImageUploadUseCase(
    private val singleImageUploadUseCase: SingleImageUploadUseCase
) {
    private val logger = KotlinLogging.logger {}

    companion object {
        private const val MAX_CONCURRENT_UPLOADS = 5
    }

    suspend fun execute(files: List<FilePart>): MultiImageUploadResults {
        logger.info { "다중 이미지 업로드 UseCase 시작: ${files.size}개 파일" }

        val semaphore = Semaphore(MAX_CONCURRENT_UPLOADS)
        val results = coroutineScope {
            files.map { filePart ->
                async(Dispatchers.IO) {
                    semaphore.withPermit {
                        uploadSingleImage(filePart)
                    }
                }
            }.awaitAll()
        }

        logger.info { "다중 이미지 업로드 UseCase 완료: ${results.size}개 결과" }
        return MultiImageUploadResults(results)
    }

    private suspend fun uploadSingleImage(filePart: FilePart): SingleImageUploadResult {
        return try {
            val uploadResult = singleImageUploadUseCase.execute(filePart)
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
                error = e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message
            )
        }
    }
}
