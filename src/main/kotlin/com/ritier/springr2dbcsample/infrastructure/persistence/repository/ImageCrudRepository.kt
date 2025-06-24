package com.ritier.springr2dbcsample.infrastructure.persistence.repository

import com.ritier.springr2dbcsample.infrastructure.entity.ImageEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageCrudRepository : ReactiveCrudRepository<ImageEntity, Long> {
}