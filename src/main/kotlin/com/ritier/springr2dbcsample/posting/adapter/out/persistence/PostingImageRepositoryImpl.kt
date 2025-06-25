package com.ritier.springr2dbcsample.posting.adapter.out.persistence

import com.ritier.springr2dbcsample.shared.constants.sql.PostingImageQueries
import com.ritier.springr2dbcsample.posting.domain.model.PostingImage
import com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter.PostingImageReadConverter
import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class PostingImageRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val postingImageReadConverter: PostingImageReadConverter
) : PostingImageRepository {
    override suspend fun findByPostingId(postingId: Long): Flow<PostingImage> =
        databaseClient.sql(PostingImageQueries.FIND_ALL_BY_POSTING_ID)
            .bind("postingId", postingId)
            .map(postingImageReadConverter::convert)
            .flow()
}