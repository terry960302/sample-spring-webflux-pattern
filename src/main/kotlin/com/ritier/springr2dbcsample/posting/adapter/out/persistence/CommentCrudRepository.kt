package com.ritier.springr2dbcsample.posting.adapter.out.persistence

import com.ritier.springr2dbcsample.shared.constants.sql.CommentQueries
import com.ritier.springr2dbcsample.posting.adapter.out.persistence.entity.CommentEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentCrudRepository : ReactiveCrudRepository<CommentEntity, Long> {
    @Query(CommentQueries.FIND_ALL_BY_POSTING_ID)
    suspend fun findByPostingId(postingId: Long): Flow<CommentEntity>
}