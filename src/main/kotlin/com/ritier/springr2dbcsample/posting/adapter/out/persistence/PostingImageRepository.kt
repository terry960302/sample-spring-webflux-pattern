package com.ritier.springr2dbcsample.posting.adapter.out.persistence

import com.ritier.springr2dbcsample.posting.domain.model.PostingImage
import kotlinx.coroutines.flow.Flow

interface PostingImageRepository {
    suspend fun findByPostingId(postingId: Long): Flow<PostingImage>
}