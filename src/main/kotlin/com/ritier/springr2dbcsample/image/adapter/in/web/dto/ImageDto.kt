package com.ritier.springr2dbcsample.image.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.vo.*
import java.time.LocalDateTime

data class ImageDto(
    val id: Long,
    val fileName: String,
    val width: Int,
    val height: Int,
    val fileSize: Long,
    val mimeType: String,
    val uploadedAt: LocalDateTime,
    val url: String?
) {
    companion object {
        fun from(image: Image): ImageDto {
            return ImageDto(
                id = image.id.value,
                url = image.url,
                fileName = image.fileName.value,
                fileSize = image.fileSize.value,
                width = image.dimensions.width,
                height = image.dimensions.height,
                uploadedAt = image.uploadedAt,
                mimeType = image.mimeType.value,
            )
        }
    }

    fun toDomain(): Image {
        return Image(
            id = ImageId(id),
            url = url,
            fileName = ImageFileName(fileName),
            dimensions = ImageDimensions(width, height),
            fileSize = FileSize(fileSize),
            uploadedAt = uploadedAt,
            mimeType = MimeType(mimeType),
        )
    }

}