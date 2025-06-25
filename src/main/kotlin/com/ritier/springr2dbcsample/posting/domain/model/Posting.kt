package com.ritier.springr2dbcsample.posting.domain.model

import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.image.domain.vo.ValidationResult
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.posting.adapter.out.persistence.entity.PostingEntity
import com.ritier.springr2dbcsample.user.domain.model.User
import java.time.LocalDateTime

data class Posting(
    val id: PostingId,
    val userId: UserId,
    val contents: String,
    val createdAt: LocalDateTime,
    val user: User? = null,
    val images: List<Image> = emptyList(),
    val comments: List<Comment> = emptyList()
) {
    init {
        require(contents.isNotBlank()) { ErrorCode.POSTING_CONTENT_EMPTY }
        require(contents.length <= MAX_CONTENT_LENGTH) { ErrorCode.POSTING_CONTENT_TOO_LONG }
    }

    fun toEntity(): PostingEntity {
        return PostingEntity(
            id = 0L,
            userId = userId.value,
            contents = contents,
            createdAt = createdAt,
        )
    }

    companion object {
        private const val MAX_CONTENT_LENGTH = 2000
        private const val MAX_IMAGES_PER_POSTING = 10

        fun fromEntity(entity: PostingEntity): Posting {
            return Posting(
                id = PostingId(entity.id),
                userId = UserId(entity.userId),
                contents = entity.contents,
                createdAt = entity.createdAt,
            )
        }

        fun create(userId: UserId, contents: String, images: List<Image> = emptyList()): Posting {
            validateContent(contents)
            validateImages(images)

            return Posting(
                id = PostingId(0L),
                userId = userId,
                contents = contents.trim(),
                createdAt = LocalDateTime.now(),
                images = images
            )
        }

        fun create(id: PostingId, user: User?, userId: UserId, createdAt: LocalDateTime, contents: String): Posting {
            validateContent(contents)

            return Posting(
                id = id,
                userId = userId,
                user = user,
                contents = contents.trim(),
                createdAt = createdAt,
            )
        }

        private fun validateContent(contents: String) {
            require(contents.isNotBlank()) { ErrorCode.POSTING_CONTENT_EMPTY.message }
            require(contents.length <= MAX_CONTENT_LENGTH) { ErrorCode.POSTING_CONTENT_TOO_LONG.message }
        }

        private fun validateImages(images: List<Image>) {
            require(images.size <= MAX_IMAGES_PER_POSTING) {
                ErrorCode.UPLOAD_FILES_SIZE_EXCEEDED.message
            }

            images.forEach { image ->
                val validationResult = image.validate()
                require(validationResult is ValidationResult.Valid) {
                    ErrorCode.INVALID_IMAGE_FORMAT.message
                }
            }
        }
    }


    // 게시물 내용 수정
    fun updateContent(newContent: String, editor: User): Posting {
        require(canBeEditedBy(editor)) { ErrorCode.FORBIDDEN.message }
        validateContent(newContent)

        return copy(contents = newContent.trim())
    }

    // 이미지 추가
    fun attachImages(newImages: List<Image>, editor: User): Posting {
        require(canBeEditedBy(editor)) { ErrorCode.FORBIDDEN.message }

        val totalImages = this.images + newImages
        validateImages(totalImages)

        return copy(images = totalImages)
    }

    // 댓글 추가 (도메인 로직)
    fun addComment(comment: Comment): Posting {
        require(comment.postingId == this.id) { ErrorCode.VALIDATION_FAILED.message }

        return copy(comments = this.comments + comment)
    }


    // 수정 권한 확인
    fun canBeEditedBy(user: User): Boolean = this.userId == user.id

    // 삭제 권한 확인
    fun canBeDeletedBy(user: User): Boolean = this.userId == user.id

    // 댓글 작성 가능 여부
    fun canAddComment(): Boolean = true // 추후 비즈니스 규칙 추가 가능

    // 이미지가 첨부 가능한 상태인지
    fun canAttachMoreImages(): Boolean = images.size < MAX_IMAGES_PER_POSTING


    fun getCommentCount(): Int = comments.size
    fun getImageCount(): Int = images.size
    fun hasImages(): Boolean = images.isNotEmpty()
    fun hasComments(): Boolean = comments.isNotEmpty()
}