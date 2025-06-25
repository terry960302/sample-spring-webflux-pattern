package com.ritier.springr2dbcsample.posting.domain.port.output

import com.ritier.springr2dbcsample.posting.domain.model.Comment
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun save(comment: Comment): Comment
    suspend fun findByPostingId(postingId: PostingId): Flow<Comment>
}