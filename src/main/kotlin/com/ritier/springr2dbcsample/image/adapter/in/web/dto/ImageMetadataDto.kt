package com.ritier.springr2dbcsample.image.adapter.`in`.web.dto

data class ImageMetadataDto(
    val name: String,
    val mimeType: String,
    val width: Int,
    val height: Int,
) {
    override fun toString(): String {
        return "ImageMetadataDto{ name : $name, mimeType : $mimeType, width : $width, height : $height}"
    }
}
