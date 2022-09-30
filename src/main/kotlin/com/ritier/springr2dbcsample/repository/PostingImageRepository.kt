package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.PostingImage
import com.ritier.springr2dbcsample.entity.converter.PostingImageReadConverter
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class PostingImageRepository {

    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @Autowired
//    private lateinit var postingImageMapper: PostingImageMapper
    private lateinit var postingImageReadConverter: PostingImageReadConverter
    suspend fun findPostingImagesByPostingId(postingId: Long): Flow<PostingImage> =
        databaseClient.sql("SELECT * FROM posting_images as pi JOIN images as i ON i.image_id = pi.image_id WHERE pi.posting_id = :postingId")
            .bind("postingId", postingId)
            .map(postingImageReadConverter::convert)
            .flow()
}