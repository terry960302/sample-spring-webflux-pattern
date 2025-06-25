package com.ritier.springr2dbcsample.image.domain.vo

@JvmInline
value class ImagePayload(val bytes: ByteArray) {
    fun size(): Long = bytes.size.toLong()
}
