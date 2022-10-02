package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Image
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : ReactiveCrudRepository<Image, Long> {
}