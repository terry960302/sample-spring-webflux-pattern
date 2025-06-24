package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.domain.repository.CommentRepository
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.presentation.dto.posting.CommentDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {

    suspend fun findByPostingId(postingId: Long): Flow<CommentDto> {
        return commentRepository.findByPostingId(PostingId(postingId))
            .map { CommentDto.from(it) }
    }
    // TODO: comment에서 user 연관관계 즉시 가져오기
}