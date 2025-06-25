package com.ritier.springr2dbcsample.posting.application.service

import com.ritier.springr2dbcsample.posting.application.command.AttachImagesToPostingCommand
import com.ritier.springr2dbcsample.posting.application.command.CreatePostingCommand
import com.ritier.springr2dbcsample.posting.application.command.UpdatePostingContentCommand
import com.ritier.springr2dbcsample.posting.domain.port.input.PostingContentUpdateUseCase
import com.ritier.springr2dbcsample.posting.domain.port.input.PostingCreationUseCase
import com.ritier.springr2dbcsample.posting.domain.port.input.PostingImageAttachmentUseCase
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.posting.domain.port.output.PostingRepository
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto.CreatePostingRequest
import com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto.PostingDto
import com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto.UpdatePostingRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostingService(
    private val postingCreationUseCase: PostingCreationUseCase,
    private val postingContentUpdateUseCase: PostingContentUpdateUseCase,
    private val postingImageAttachmentUseCase: PostingImageAttachmentUseCase,
    private val postingRepository: PostingRepository // 단순 조회용
) {
    private val logger = KotlinLogging.logger {}

    suspend fun createPosting(request: CreatePostingRequest): PostingDto {
        val command = CreatePostingCommand(
            userId = request.userId,
            contents = request.contents,
            images = request.images.map { it.toDomain() }
        )

        val createdPosting = postingCreationUseCase.execute(command)
        logger.info { "게시물 생성 완료: ${createdPosting.id.value}" }

        return PostingDto.from(createdPosting)
    }
    suspend fun updatePostingContent(postingId: Long, request: UpdatePostingRequest): PostingDto {
        val command = UpdatePostingContentCommand(
            postingId = postingId,
            editorUserId = request.editorUserId,
            newContent = request.contents
        )

        val updatedPosting = postingContentUpdateUseCase.execute(command)
        logger.info { "게시물 수정 완료: ${updatedPosting.id.value}" }

        return PostingDto.from(updatedPosting)
    }
    suspend fun attachImagesToPosting(postingId: Long, editorUserId: Long, images: List<Image>): PostingDto {
        val command = AttachImagesToPostingCommand(
            postingId = postingId,
            editorUserId = editorUserId,
            images = images
        )

        val updatedPosting = postingImageAttachmentUseCase.execute(command)
        logger.info { "게시물 이미지 첨부 완료: ${updatedPosting.id.value}" }

        return PostingDto.from(updatedPosting)
    }

    @Transactional(readOnly = true)
    suspend fun findPostingById(id: Long): PostingDto {
        val posting = postingRepository.findById(PostingId(id))
            ?: throw AppException(ErrorCode.POSTING_NOT_FOUND)
        logger.info { "게시물 조회 성공 - $id" }
        return PostingDto.from(posting)
    }

    @Transactional(readOnly = true)
    suspend fun findAllPostings(): Flow<PostingDto> {
        return postingRepository.findAll()
            .map { PostingDto.from(it) }
    }

    @Transactional(readOnly = true)
    suspend fun findPostingsByUserId(userId: Long): Flow<PostingDto> {
        return postingRepository.findByUserId(UserId(userId))
            .map { PostingDto.from(it) }
    }

    suspend fun deletePosting(postingId: Long, userId: Long): Boolean {
        val posting = postingRepository.findById(PostingId(postingId))
            ?: throw AppException(ErrorCode.POSTING_NOT_FOUND)

        // 도메인 규칙 확인 (간단한 경우는 UseCase 없이 직접 체크)
        require(posting.userId.value == userId) { ErrorCode.FORBIDDEN.message }

        return postingRepository.deleteById(PostingId(postingId))
    }

// FIXME: > suspend fun findAll(): Flow<PostingDto> = postingRepository.findAll().asFlow().map(this::loadRelations)
// Took average 300ms~500ms to take response from client.(too slow)
// !!! non-blocking 과 관리용이성을 보장하면서 성능을 개선하는 방법을 보완할 필요 !!!
//    @Autowired
//    private lateinit var postingRepository: PostingRepository
//    @Autowired
//    private lateinit var postingImageRepository: PostingImageRepository
//    @Autowired
//    private lateinit var userRepository: UserRepository
//    suspend fun loadRelations(posting: Posting): PostingDto = withContext(Dispatchers.IO) {
//        val deferredUser = async { userRepository.findById(posting.userId) }
//        val deferredImages = async { postingImageRepository.findPostingImagesByPostingId(posting.id) }
//        posting.user = deferredUser.await()
//        posting.images = deferredImages.await().map { it.image!! }.toList()
//        PostingDto.from(posting)
//    }
}