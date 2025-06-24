package com.ritier.springr2dbcsample.infrastructure.persistence.repository

import com.ritier.springr2dbcsample.common.constants.sql.PostingQueries
import com.ritier.springr2dbcsample.domain.model.Posting
import com.ritier.springr2dbcsample.domain.repository.PostingRepository
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import com.ritier.springr2dbcsample.infrastructure.persistence.converter.PostingAggregateConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.*
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class PostingRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val postingAggregateConverter: PostingAggregateConverter
) : PostingRepository {

    override suspend fun findById(id: PostingId): Posting? {
        return databaseClient.sql(PostingQueries.FETCH_SINGLE_POSTING)
            .bind("postingId", id.value)
            .fetch()
            .all()
            .bufferUntilChanged<String> { row -> row["posting_id"].toString() }
            .map { rowList -> postingAggregateConverter.convertRowListToPosting(rowList) }
            .awaitFirstOrNull()
    }

    override suspend fun findAll(): Flow<Posting> {
        return databaseClient.sql(PostingQueries.FETCH_ALL_POSTINGS)
            .fetch()
            .all()
            .processPostingsRawData()
    }

    override suspend fun findByUserId(userId: UserId): Flow<Posting> {
        return databaseClient.sql(PostingQueries.FETCH_ALL_POSTINGS_BY_USER_ID)
            .bind("userId", userId.value)
            .fetch()
            .all()
            .processPostingsRawData()
    }

    private fun Flux<Map<String, *>>.processPostingsRawData(): Flow<Posting> {
        return this
            .switchIfEmpty(Flux.empty())
            .bufferUntilChanged<String> { it["posting_id"].toString() }
            .map { rowList -> postingAggregateConverter.convertRowListToPosting(rowList) }
            .asFlow()
    }
}