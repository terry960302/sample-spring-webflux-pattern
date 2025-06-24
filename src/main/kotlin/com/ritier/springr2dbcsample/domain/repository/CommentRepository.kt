package com.ritier.springr2dbcsample.domain.repository

import com.ritier.springr2dbcsample.domain.model.Comment
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun findByPostingId(postingId: PostingId): Flow<Comment>
}