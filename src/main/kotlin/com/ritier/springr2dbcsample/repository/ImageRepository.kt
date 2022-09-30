package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.PostingImage
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : ReactiveCrudRepository<Image, Long> {
//    @Query("SELECT i.* FROM images as i JOIN posting_images as pi ON pi.image_id = i.image_id WHERE pi.posting_id = :postingId")
//    suspend fun findImagesByPostingId(postingId : Long) : Flow<Image>
}