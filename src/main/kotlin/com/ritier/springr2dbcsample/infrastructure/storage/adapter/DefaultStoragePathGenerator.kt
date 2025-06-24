package com.ritier.springr2dbcsample.infrastructure.storage.adapter

import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.infrastructure.storage.port.StoragePathGenerator
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class DefaultStoragePathGenerator : StoragePathGenerator {
    override fun generatePath(image: Image, directory: String): String {
        val datePath = image.uploadedAt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val sanitizedFileName = image.fileName.sanitized().value
        return "$directory/$datePath/$sanitizedFileName"
    }
}
