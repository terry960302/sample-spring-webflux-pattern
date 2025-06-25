package com.ritier.springr2dbcsample.image.domain.port.output

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.vo.ImagePayload
import com.ritier.springr2dbcsample.image.domain.vo.StorageResult

interface ImageStoragePort {
    suspend fun upload(image: Image, payload: ImagePayload): StorageResult
    suspend fun delete(imagePath: String): Boolean
    suspend fun exists(imagePath: String): Boolean
}