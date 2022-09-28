package com.ritier.springr2dbcsample.service

import com.google.cloud.storage.*
import com.ritier.springr2dbcsample.dto.ImageMetadataDto
import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


@Component
class ImageService {

    @Autowired
    private lateinit var env: Environment
    @Autowired
    private lateinit var imageRepository: ImageRepository
    private val GCP_SOTRAGE_PREFIX = "https://storage.googleapis.com/"

    suspend fun saveImage(image: Image): Image = imageRepository.save(image).awaitSingle()

    // CPU use ->  use Dispatchers.Default
    // Network or Disk bound -> use Dispatchers.IO
    suspend fun uploadImage(file: FilePart): String = withContext(Dispatchers.IO) {
        val PROJECT_ID: String = env.getProperty("spring.storage.project-id")!!
        val BUCKET_NAME: String = env.getProperty("spring.storage.bucket-name")!!

        val storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().service
        val objectName: String = file.filename()
        val blobId: BlobId = BlobId.of(BUCKET_NAME, objectName)
        val blobInfo = BlobInfo.newBuilder(blobId).build()

        storage.create(blobInfo, file.toBytes());
        val url = "$GCP_SOTRAGE_PREFIX$BUCKET_NAME/$objectName"
        url
    }

    private suspend fun FilePart.toBytes(): ByteArray {
        val bytesList: List<ByteArray> = this.content()
            .flatMap { dataBuffer -> Flux.just(dataBuffer.asByteBuffer().array()) }
            .collectList()
            .awaitFirst()

        // concat ByteArrays
        val byteStream = ByteArrayOutputStream()
        bytesList.forEach { bytes -> byteStream.write(bytes) }
        return byteStream.toByteArray()
    }

    private fun sortImagesByReqOrder(): List<ImageMetadataDto> {
        return listOf()
    }

    private suspend fun fetchImageMetadata(file: File) = null
}