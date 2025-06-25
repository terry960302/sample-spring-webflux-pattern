package com.ritier.springr2dbcsample.posting.domain.port.input

import com.ritier.springr2dbcsample.posting.application.command.AddCommentToPostingCommand
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.posting.domain.model.Comment
import com.ritier.springr2dbcsample.posting.domain.port.output.CommentRepository
import com.ritier.springr2dbcsample.posting.domain.port.output.PostingRepository
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.posting.domain.vo.CommentId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CommentAdditionUseCase(
    private val postingRepository: PostingRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: AddCommentToPostingCommand): Comment {
        // 게시물 조회
        val posting = postingRepository.findById(PostingId(command.postingId))
            ?: throw AppException(ErrorCode.POSTING_NOT_FOUND)

        // 댓글 작성자 조회
        val author = userRepository.findById(UserId(command.authorUserId))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        // 댓글 작성 권한 확인
        require(author.canCreateComment()) { ErrorCode.FORBIDDEN.message }
        require(posting.canAddComment()) { ErrorCode.FORBIDDEN.message }

        val comment = Comment(
            id = CommentId(0),
            userId = UserId(command.authorUserId),
            postingId = PostingId(command.postingId),
            contents = command.contents,
            createdAt = LocalDateTime.now()
        )

        val savedComment = commentRepository.save(comment)

        // 도메인 객체의 addComment 메서드 활용
        val updatedPosting = posting.addComment(savedComment)
        postingRepository.save(updatedPosting)

        logger.info { "댓글 추가 UseCase 실행 완료: ${savedComment.id.value}" }
        return savedComment
    }
}