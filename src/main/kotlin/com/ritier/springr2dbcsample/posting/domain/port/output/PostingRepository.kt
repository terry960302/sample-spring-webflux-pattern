package com.ritier.springr2dbcsample.posting.domain.port.output

import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import kotlinx.coroutines.flow.Flow

interface PostingRepository {
    suspend fun save(posting: Posting): Posting
    suspend fun findById(id: PostingId): Posting?
    suspend fun findAll(): Flow<Posting>
    suspend fun findByUserId(userId: UserId): Flow<Posting>
    suspend fun deleteById(id : PostingId) : Boolean
}