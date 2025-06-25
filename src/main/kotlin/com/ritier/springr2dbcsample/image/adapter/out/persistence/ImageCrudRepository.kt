package com.ritier.springr2dbcsample.image.adapter.out.persistence

import com.ritier.springr2dbcsample.image.adapter.out.persistence.entity.ImageEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageCrudRepository : ReactiveCrudRepository<ImageEntity, Long> {
}