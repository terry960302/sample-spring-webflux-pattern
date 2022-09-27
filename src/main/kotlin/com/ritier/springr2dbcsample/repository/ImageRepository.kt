package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.PostingImage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : ReactiveCrudRepository<Image, Long> {

}

@Repository
class PostingImageRepository {
    @Autowired
    private lateinit var databaseClient: DatabaseClient
}