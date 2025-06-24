package com.ritier.springr2dbcsample.infrastructure.persistence.repository

import com.ritier.springr2dbcsample.domain.model.Image
import com.ritier.springr2dbcsample.domain.repository.ImageRepository
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