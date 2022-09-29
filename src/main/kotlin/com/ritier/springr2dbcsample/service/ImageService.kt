package com.ritier.springr2dbcsample.service

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import com.ritier.springr2dbcsample.dto.ImageDto
import com.ritier.springr2dbcsample.dto.ImageMetadataDto
import com.ritier.springr2dbcsample.dto.toEntity
import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.imageio.ImageIO
import javax.imageio.stream.MemoryCacheImageInputStream


@Component
class ImageService {

    @Autowired
    private lateinit var env: Environment

    @Autowired
    private lateinit var imageRepository: ImageRepository
    private val GCP_SOTRAGE_PREFIX = "https://storage.googleapis.com"

    suspend fun saveImage(image: ImageDto): ImageDto =
        ImageDto.from(imageRepository.save(image.toEntity()).awaitSingle())

    // CPU use ->  use Dispatchers.Default
    // Network or Disk bound -> use Dispatchers.IO
    suspend fun uploadImage(file: FilePart): String = withContext(Dispatchers.IO) {
        val projectId: String = env.getProperty("spring.storage.project-id")!!
        val bucketName: String = env.getProperty("spring.storage.bucket-name")!!

        try {
            val storage = StorageOptions.newBuilder().setProjectId(projectId).build().service
            val objectName: String = file.filename().replace(" ", "_")
            val folder = "test"
            val dst = "$folder/$objectName"
            val blobId: BlobId = BlobId.of(bucketName, dst)
            val metadata: ImageMetadataDto = fetchImageMetadata(file)
            val blobInfo = BlobInfo.newBuilder(blobId).setContentType(metadata.mimeType).build()

            storage.create(blobInfo, file.toBytes());
            val url = "$GCP_SOTRAGE_PREFIX/$bucketName/$dst"
            url
        } catch (e: Error) {
            throw e
        }
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

    suspend fun fetchImageMetadata(file: FilePart): ImageMetadataDto {
        val log = LogManager.getLogger()

        try {
            val inputStream: InputStream? = file.content().awaitFirstOrNull()?.asInputStream()

            val bufferedImage = withContext(Dispatchers.IO) {
                ImageIO.read(inputStream)
            }

            val name = file.filename().replace(" ", "_")
            val width: Int = bufferedImage.width
            val height: Int = bufferedImage.height
            val mimeType = file.headers().contentType.toString()

            val metadata = ImageMetadataDto(
                name = name,
                width = width,
                height = height,
                mimeType = mimeType
            )
            log.info(metadata.toString())

            return metadata
        } catch (e: IOException) {
            log.error(e.message)
            throw e
        }
    }
}