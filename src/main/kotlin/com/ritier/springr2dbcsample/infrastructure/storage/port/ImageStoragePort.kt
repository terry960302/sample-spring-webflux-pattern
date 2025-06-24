package com.ritier.springr2dbcsample.infrastructure.storage.port

import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.domain.vo.image.ImagePayload
import com.ritier.springr2dbcsample.domain.vo.image.StorageResult

interface ImageStoragePort {
    suspend fun upload(image: Image, payload: ImagePayload): StorageResult
    suspend fun delete(imagePath: String): Boolean
    suspend fun exists(imagePath: String): Boolean
}