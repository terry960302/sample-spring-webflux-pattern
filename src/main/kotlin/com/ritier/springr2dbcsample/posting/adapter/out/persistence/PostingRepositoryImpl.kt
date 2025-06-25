package com.ritier.springr2dbcsample.posting.adapter.out.persistence

import com.ritier.springr2dbcsample.shared.constants.sql.PostingQueries
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter.PostingAggregateConverter
import com.ritier.springr2dbcsample.posting.domain.port.output.PostingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.*
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class PostingRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val crudRepository: PostingCrudRepository,
    private val postingAggregateConverter: PostingAggregateConverter
) : PostingRepository {
    override suspend fun save(posting: Posting): Posting {
        val saved = crudRepository.save(posting.toEntity()).awaitSingle()
        return Posting.fromEntity(saved)
    }

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

    override suspend fun deleteById(id: PostingId): Boolean {
        return try {
            crudRepository.deleteById(id.value)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun Flux<Map<String, *>>.processPostingsRawData(): Flow<Posting> {
        return this
            .switchIfEmpty(Flux.empty())
            .bufferUntilChanged<String> { it["posting_id"].toString() }
            .map { rowList -> postingAggregateConverter.convertRowListToPosting(rowList) }
            .asFlow()
    }
}