package com.ritier.springr2dbcsample.posting.domain.port.input

import com.ritier.springr2dbcsample.posting.application.command.UpdatePostingContentCommand
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.posting.domain.port.output.PostingRepository
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class PostingContentUpdateUseCase(
    private val postingRepository: PostingRepository,
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: UpdatePostingContentCommand): Posting {
        // 게시물 조회
        val posting = postingRepository.findById(PostingId(command.postingId))
            ?: throw AppException(ErrorCode.POSTING_NOT_FOUND)

        // 수정자 조회
        val editor = userRepository.findById(UserId(command.editorUserId))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        // 권한 체크 포함
        val updatedPosting = posting.updateContent(command.newContent, editor)
        val savedPosting = postingRepository.save(updatedPosting)

        logger.info { "게시물 내용 수정 UseCase 실행 완료: ${savedPosting.id.value}" }
        return savedPosting
    }
}