package com.ritier.springr2dbcsample.image.domain.vo

data class ImageDimensions(val width: Int, val height: Int) {
    companion object {
        private const val MIN_DIMENSION = 1
        private const val MAX_DIMENSION = 10000
    }

    init {
        require(width >= MIN_DIMENSION && height >= MIN_DIMENSION) {
            "이미지 크기는 ${MIN_DIMENSION}x$MIN_DIMENSION 이상이어야 합니다"
        }
        require(width <= MAX_DIMENSION && height <= MAX_DIMENSION) {
            "이미지 크기는 ${MAX_DIMENSION}x$MAX_DIMENSION 이하여야 합니다"
        }
    }

    val totalPixels: Long get() = width.toLong() * height.toLong()

    fun isWebFriendly(): Boolean = width <= 1920 && height <= 1080

    fun aspectRatio(): Double = width.toDouble() / height.toDouble()
}