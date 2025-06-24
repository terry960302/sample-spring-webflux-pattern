package com.ritier.springr2dbcsample.domain.repository

import com.ritier.springr2dbcsample.domain.model.Image

interface ImageRepository {
    suspend fun save(image: Image) : Image
}