package com.ritier.springr2dbcsample.domain.vo.image

@JvmInline
value class ImagePayload(val bytes: ByteArray) {
    fun size(): Long = bytes.size.toLong()
}
