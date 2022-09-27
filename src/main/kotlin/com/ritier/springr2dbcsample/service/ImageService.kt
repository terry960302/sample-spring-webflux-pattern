package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.PostingImage
import com.ritier.springr2dbcsample.repository.ImageRepository
import com.ritier.springr2dbcsample.repository.PostingImageRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import java.io.File

class ImageService {
    @Autowired
    private lateinit var imageRepository: ImageRepository

    suspend fun saveImage(image : Image) = imageRepository.save(image).awaitSingle()

    suspend fun uploadImage(url:String) = coroutineScope {
        // TODO: 간단 이미지 업로드 및 디비 저장
    }

    suspend fun fetchImageMetadata(file : File) = null
}