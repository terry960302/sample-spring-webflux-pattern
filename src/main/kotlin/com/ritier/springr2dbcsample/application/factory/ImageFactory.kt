package com.ritier.springr2dbcsample.application.factory

import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.domain.vo.image.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.withContext
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import javax.imageio.ImageIO

@Component
class ImageFactory {
    companion object {
        suspend fun fromFilePart(filePart: FilePart): Pair<Image, ImagePayload> {
            val name = filePart.filename()
                ?: throw AppException(ErrorCode.INVALID_FILENAME)

            val contentType = filePart.headers().contentType?.toString()
                ?: throw AppException(ErrorCode.UNSUPPORTED_FILE_TYPE)

            val bytes = filePart.content()
                .flatMap { Flux.just(it.asByteBuffer().array()) }
                .collectList()
                .awaitFirst()
                .let { chunks ->
                    ByteArrayOutputStream().apply {
                        chunks.forEach(::write)
                    }.toByteArray()
                }

            val payload = ImagePayload(bytes)
            val dimensions = extractDimensions(bytes)

            val image = Image(
                fileName = ImageFileName(name),
                dimensions = dimensions,
                mimeType = MimeType(contentType),
                fileSize = FileSize(payload.size()),
                uploadedAt = LocalDateTime.now()
            )

            val validationResult = image.validate()
            if (validationResult is ValidationResult.Invalid) {
                throw AppException(ErrorCode.INVALID_IMAGE_FORMAT)
            }

            return image to payload
        }

        private suspend fun extractDimensions(bytes: ByteArray): ImageDimensions {
            return try {
                val bufferedImage = withContext(Dispatchers.IO) {
                    ImageIO.read(ByteArrayInputStream(bytes))
                } ?: throw AppException(ErrorCode.INVALID_IMAGE_FORMAT)

                ImageDimensions(bufferedImage.width, bufferedImage.height)
            } catch (e: Exception) {
                throw AppException(ErrorCode.IMAGE_PROCESSING_FAILED, e)
            }
        }
    }
}
