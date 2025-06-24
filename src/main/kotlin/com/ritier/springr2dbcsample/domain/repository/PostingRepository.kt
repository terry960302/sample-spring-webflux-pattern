package com.ritier.springr2dbcsample.domain.repository

import com.ritier.springr2dbcsample.domain.model.Posting
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import kotlinx.coroutines.flow.Flow

interface PostingRepository {
    suspend fun findById(id: PostingId): Posting?
    suspend fun findAll(): Flow<Posting>
    suspend fun findByUserId(userId: UserId): Flow<Posting>
}