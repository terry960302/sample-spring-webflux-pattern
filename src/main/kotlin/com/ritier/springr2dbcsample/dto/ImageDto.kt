package com.ritier.springr2dbcsample.dto

import com.ritier.springr2dbcsample.entity.Image
import java.time.LocalDateTime

data class ImageDto(
    val id: Long,
    val url: String,
    val width: Int,
    val height: Int,
    val createdAt: LocalDateTime
) {
    companion object Mapper {
        fun from(image: Image): ImageDto {
            return ImageDto(
                id = image.id,
                url = image.url,
                width = image.width,
                height = image.height,
                createdAt = image.createdAt
            )
        }
    }
}

fun ImageDto.toEntity(): Image {
    return Image(
        id = this.id,
        url = this.url,
        width = this.width,
        height = this.height,
        createdAt = this.createdAt
    )
}
