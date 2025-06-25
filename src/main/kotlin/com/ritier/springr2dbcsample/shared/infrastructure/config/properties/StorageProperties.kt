package com.ritier.springr2dbcsample.shared.infrastructure.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "app.storage")
@ConstructorBinding
data class StorageProperties(
    val credPath: String,
    val projectId: String,
    val bucketName: String,
    val baseUrl: String = "https://storage.googleapis.com"
) {
}