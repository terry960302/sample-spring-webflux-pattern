package com.ritier.springr2dbcsample.posting.domain.port.input

import com.ritier.springr2dbcsample.posting.application.command.CreatePostingCommand
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.posting.domain.port.output.PostingRepository
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import mu.KotlinLogging
import org.springframework.stereotype.Component


@Component
class PostingCreationUseCase(
    private val postingRepository: PostingRepository,
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: CreatePostingCommand): Posting {
        // 사용자 존재 확인
        val user = userRepository.findById(UserId(command.userId))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        // 도메인 규칙: 게시물 작성 권한 확인
        require(user.canCreatePosting()) { ErrorCode.FORBIDDEN.message }

        // 도메인 객체의 create 메서드 활용
        val posting = Posting.create(
            userId = UserId(command.userId),
            contents = command.contents,
            images = command.images
        )

        val savedPosting = postingRepository.save(posting)
        logger.info { "게시물 생성 UseCase 실행 완료: ${savedPosting.id.value}" }

        return savedPosting
    }
}
