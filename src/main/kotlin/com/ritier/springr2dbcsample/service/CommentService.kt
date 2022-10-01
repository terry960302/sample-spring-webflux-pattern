package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.dto.CommentDto
import com.ritier.springr2dbcsample.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService {
    @Autowired
    private lateinit var commentRepository: CommentRepository

    suspend fun findCommentsByPostingId(postingId: Long): Flow<CommentDto> =
        commentRepository.findCommentsByPostingId(postingId).map { CommentDto.from(it) }
}