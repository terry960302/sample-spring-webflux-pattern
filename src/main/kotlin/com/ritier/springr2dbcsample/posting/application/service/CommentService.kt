package com.ritier.springr2dbcsample.posting.application.service

import com.ritier.springr2dbcsample.posting.application.command.AddCommentToPostingCommand
import com.ritier.springr2dbcsample.posting.domain.port.input.CommentAdditionUseCase
import com.ritier.springr2dbcsample.posting.domain.port.output.CommentRepository
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto.CommentDto
import com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto.CreateCommentRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentAdditionUseCase: CommentAdditionUseCase,
    private val commentRepository: CommentRepository // 단순 조회용
) {
    private val logger = KotlinLogging.logger {}

    suspend fun addComment(request: CreateCommentRequest): CommentDto {
        val command = AddCommentToPostingCommand(
            postingId = request.postingId,
            authorUserId = request.authorUserId,
            contents = request.contents
        )

        val createdComment = commentAdditionUseCase.execute(command)
        logger.info { "댓글 추가 완료: ${createdComment.id.value}" }

        return CommentDto.from(createdComment)
    }

    @Transactional(readOnly = true)
    suspend fun findByPostingId(postingId: Long): Flow<CommentDto> {
        return commentRepository.findByPostingId(PostingId(postingId))
            .map { CommentDto.from(it) }
    }
    // TODO: comment에서 user 연관관계 즉시 가져오기
}