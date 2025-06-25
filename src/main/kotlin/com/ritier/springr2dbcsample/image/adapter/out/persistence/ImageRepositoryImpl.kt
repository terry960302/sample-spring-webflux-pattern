package com.ritier.springr2dbcsample.image.adapter.out.persistence

import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.port.output.ImageRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class ImageRepositoryImpl(
    private val crudRepository: ImageCrudRepository
) : ImageRepository {

    override suspend fun save(image: Image): Image {
        val saved = crudRepository.save(image.toEntity()).awaitSingle();
        return Image.fromEntity(saved);
    }
}