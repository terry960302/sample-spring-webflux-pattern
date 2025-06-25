package com.ritier.springr2dbcsample.posting.domain.port.input

import com.ritier.springr2dbcsample.posting.application.command.AttachImagesToPostingCommand
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
class PostingImageAttachmentUseCase(
    private val postingRepository: PostingRepository,
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: AttachImagesToPostingCommand): Posting {
        // 게시물 조회
        val posting = postingRepository.findById(PostingId(command.postingId))
            ?: throw AppException(ErrorCode.POSTING_NOT_FOUND)

        // 수정자 조회
        val editor = userRepository.findById(UserId(command.editorUserId))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        // 도메인 객체의 attachImages 메서드 활용 (권한 체크 및 이미지 검증 포함)
        val updatedPosting = posting.attachImages(command.images, editor)
        val savedPosting = postingRepository.save(updatedPosting)

        logger.info { "게시물 이미지 첨부 UseCase 실행 완료: ${savedPosting.id.value}" }
        return savedPosting
    }
}