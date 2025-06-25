package com.ritier.springr2dbcsample.image.domain.vo

@JvmInline
value class FileSize(val value: Long) {
    companion object {
        private const val MAX_SIZE = 50_000_000L // 50MB
    }

    init {
        require(value >= 0) { "파일 크기는 0 이상이어야 합니다" }
        require(value <= MAX_SIZE) { "파일 크기가 최대 허용 크기를 초과했습니다" }
    }

    fun isOptimal(): Boolean = value <= 2_097_152L // 2MB

    fun toHumanReadable(): String = when {
        value < 1024 -> "${value}B"
        value < 1024 * 1024 -> "${value / 1024}KB"
        value < 1024 * 1024 * 1024 -> "${value / (1024 * 1024)}MB"
        else -> "${value / (1024 * 1024 * 1024)}GB"
    }
}