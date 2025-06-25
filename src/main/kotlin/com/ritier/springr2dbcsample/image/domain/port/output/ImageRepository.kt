package com.ritier.springr2dbcsample.image.domain.port.output

import com.ritier.springr2dbcsample.image.domain.model.Image

interface ImageRepository {
    suspend fun save(image: Image) : Image
}