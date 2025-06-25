package com.ritier.springr2dbcsample.image.domain.port.output

import com.ritier.springr2dbcsample.image.domain.model.Image

interface StoragePathGenerator {
    fun generatePath(image: Image, directory: String): String
}