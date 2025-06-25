package com.ritier.springr2dbcsample.image.domain.model

import com.ritier.springr2dbcsample.shared.util.ConverterUtil
import com.ritier.springr2dbcsample.image.domain.vo.*
import com.ritier.springr2dbcsample.image.adapter.out.persistence.entity.ImageEntity
import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageDto
import io.r2dbc.spi.Row
import java.time.LocalDateTime

data class Image(
    val id: ImageId = ImageId(0),
    val fileName: ImageFileName,
    val dimensions: ImageDimensions,
    val mimeType: MimeType,
    val fileSize: FileSize,
    val uploadedAt: LocalDateTime = LocalDateTime.now(),
    val url: String? = null
) {
    companion object {
        private const val MAX_FILE_SIZE = 10_485_760L // 10MB
        private const val MAX_TOTAL_PIXELS = 50_000_000L
        private const val WEB_MAX_WIDTH = 1920
        private const val WEB_MAX_HEIGHT = 1080
        private const val OPTIMIZATION_THRESHOLD = 2_097_152L // 2MB
        private val WEB_FRIENDLY_TYPES = setOf("image/jpeg", "image/png", "image/webp")


        fun fromEntity(entity: ImageEntity): Image {
            return Image(
                id = ImageId(entity.id),
                fileName = ImageFileName(entity.fileName),
                fileSize = FileSize(entity.fileSize),
                dimensions = ImageDimensions(entity.width, entity.height),
                mimeType = MimeType(entity.mimeType),
                uploadedAt = entity.uploadedAt,
                url = entity.url,
            )
        }

        fun fromRow(row: Row): Image {
            return fromRow(row)
        }

        fun fromRow(row: Map<String, *>): Image {
            return Image(
                id = ImageId(row["image_id"].toString().toLong()),
                url = row["url"].toString(),
                dimensions = ImageDimensions(row["width"].toString().toInt(), row["height"].toString().toInt()),
                fileName = ImageFileName(row["file_name"].toString()),
                fileSize = FileSize(row["file_size"].toString().toLong()),
                mimeType = MimeType(row["mime_type"].toString()),
                uploadedAt = ConverterUtil.convertStrToLocalDateTime(row["uploaded_at"].toString())
            )
        }

        fun fromRowWithPrefix(prefix: String, row: Row): Image? {
            if (row["${prefix}id"] == null) return null
            return Image(
                id = ImageId(row["${prefix}id"].toString().toLong()),
                url = row["${prefix}url"].toString(),
                dimensions = ImageDimensions(
                    row["${prefix}width"].toString().toInt(),
                    row["${prefix}height"].toString().toInt()
                ),
                fileName = ImageFileName(row["${prefix}file_name"].toString()),
                fileSize = FileSize(row["${prefix}file_size"].toString().toLong()),
                mimeType = MimeType(row["${prefix}mime_type"].toString()),
                uploadedAt = ConverterUtil.convertStrToLocalDateTime(row["${prefix}uploaded_at"].toString())
            )
        }

        fun fromRowWithPrefix(prefix: String, row: Map<String, *>): Image {
            return Image(
                id = ImageId(row["${prefix}id"].toString().toLong()),
                url = row["${prefix}url"].toString(),
                dimensions = ImageDimensions(
                    row["${prefix}width"].toString().toInt(),
                    row["${prefix}height"].toString().toInt()
                ),
                fileName = ImageFileName(row["${prefix}file_name"].toString()),
                fileSize = FileSize(row["${prefix}file_size"].toString().toLong()),
                mimeType = MimeType(row["${prefix}mime_type"].toString()),
                uploadedAt = ConverterUtil.convertStrToLocalDateTime(row["${prefix}uploaded_at"].toString())
            )
        }
    }

    fun validate(): ValidationResult {
        val violations = buildList {
            if (fileSize.value > MAX_FILE_SIZE) add("파일 크기가 ${MAX_FILE_SIZE}바이트를 초과했습니다")
            if (dimensions.totalPixels > MAX_TOTAL_PIXELS) add("픽셀 수가 ${MAX_TOTAL_PIXELS}를 초과했습니다")
            if (!mimeType.isImageType()) add("지원하지 않는 파일 형식입니다")
        }
        return if (violations.isEmpty()) ValidationResult.Valid
        else ValidationResult.Invalid(violations)
    }

    fun isWebOptimized(): Boolean =
        dimensions.width <= WEB_MAX_WIDTH &&
                dimensions.height <= WEB_MAX_HEIGHT &&
                fileSize.value <= OPTIMIZATION_THRESHOLD &&
                WEB_FRIENDLY_TYPES.contains(mimeType.value)

    fun needsOptimization(): Boolean =
        fileSize.value > OPTIMIZATION_THRESHOLD ||
                dimensions.totalPixels > MAX_TOTAL_PIXELS / 2

    fun toDto(): ImageDto = ImageDto(
        id = id.value,
        fileName = fileName.value,
        width = dimensions.width,
        height = dimensions.height,
        fileSize = fileSize.value,
        mimeType = mimeType.value,
        uploadedAt = uploadedAt,
        url = url
    )

    fun toEntity(): ImageEntity = ImageEntity(
        id = id.value,
        fileName = fileName.value,
        width = dimensions.width,
        height = dimensions.height,
        fileSize = fileSize.value,
        mimeType = mimeType.value,
        uploadedAt = uploadedAt,
        url = url
    )
}