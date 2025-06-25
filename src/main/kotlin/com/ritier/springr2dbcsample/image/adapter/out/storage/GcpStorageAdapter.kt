package com.ritier.springr2dbcsample.image.adapter.out.storage

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.ritier.springr2dbcsample.shared.infrastructure.config.properties.StorageProperties
import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.vo.ImagePayload
import com.ritier.springr2dbcsample.image.domain.vo.StorageResult
import com.ritier.springr2dbcsample.image.domain.port.output.ImageStoragePort
import com.ritier.springr2dbcsample.image.domain.port.output.StoragePathGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class GcpStorageAdapter(
    private val storageProperties: StorageProperties,
    private val pathGenerator: StoragePathGenerator
) : ImageStoragePort {

    companion object {
        private const val CACHE_MAX_AGE = 31536000
    }

    private val storage: Storage by lazy {
        StorageOptions.newBuilder()
            .setProjectId(storageProperties.projectId)
            .build()
            .service
    }

    override suspend fun upload(image: Image, payload: ImagePayload): StorageResult {
        return try {
            val path = pathGenerator.generatePath(image, "images")
            val blobInfo = createBlobInfo(image, path)

            withContext(Dispatchers.IO) {
                storage.create(blobInfo, payload.bytes)
            }

            val publicUrl = "${storageProperties.baseUrl}/${storageProperties.bucketName}/$path"
            StorageResult.Success(publicUrl)
        } catch (e: Exception) {
            StorageResult.Failure(e.message ?: "스토리지 업로드 실패")
        }
    }

    override suspend fun delete(imagePath: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                storage.delete(BlobId.of(storageProperties.bucketName, imagePath))
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun exists(imagePath: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                storage.get(BlobId.of(storageProperties.bucketName, imagePath)) != null
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun createBlobInfo(image: Image, path: String): BlobInfo {
        val blobId = BlobId.of(storageProperties.bucketName, path)
        return BlobInfo.newBuilder(blobId)
            .setContentType(image.mimeType.value)
            .setCacheControl("public, max-age=$CACHE_MAX_AGE")
            .build()
    }
}