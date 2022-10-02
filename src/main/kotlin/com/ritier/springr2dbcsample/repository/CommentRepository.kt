package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Comment
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository  : ReactiveCrudRepository<Comment, Long>{
    @Query("SELECT c.* FROM posting_comments as c WHERE c.posting_id = :postingId")
    suspend fun findCommentsByPostingId(postingId : Long) : Flow<Comment>
}