package com.ritier.springr2dbcsample.infrastructure.storage.port

import com.ritier.springr2dbcsample.domain.model.Image

interface StoragePathGenerator {
    fun generatePath(image: Image, directory: String): String
}