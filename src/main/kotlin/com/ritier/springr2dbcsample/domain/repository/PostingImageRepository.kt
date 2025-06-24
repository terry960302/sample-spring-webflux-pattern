package com.ritier.springr2dbcsample.domain.repository

import com.ritier.springr2dbcsample.domain.model.PostingImage
import kotlinx.coroutines.flow.Flow

interface PostingImageRepository {
    suspend fun findByPostingId(postingId: Long): Flow<PostingImage>
}