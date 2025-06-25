package com.ritier.springr2dbcsample.posting.domain.model

import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.posting.domain.vo.CommentId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.posting.adapter.out.persistence.entity.CommentEntity
import com.ritier.springr2dbcsample.user.domain.model.User
import java.time.LocalDateTime

data class Comment(
    val id: CommentId,
    val userId: UserId,
    val postingId: PostingId,
    val contents: String,
    val createdAt: LocalDateTime,
    val user: User? = null
) {

    fun toEntity(): CommentEntity {
        return CommentEntity(
            id = id.value,
            userId = userId.value,
            postingId = postingId.value,
            contents = contents,
            createdAt = createdAt,
        )
    }

    companion object {
        private const val MAX_LENGTH = 500;

        fun fromEntity(entity: CommentEntity): Comment {
            return Comment(
                id = CommentId(entity.id),
                postingId = PostingId(entity.postingId),
                userId = UserId(entity.userId),
                contents = entity.contents,
                createdAt = entity.createdAt
            )
        }
    }

    init {
        require(contents.isNotBlank()) { ErrorCode.COMMENT_CONTENT_EMPTY.message }
        require(contents.length <= MAX_LENGTH) { ErrorCode.COMMENT_CONTENT_TOO_LONG.message }
    }

    fun isOwnedBy(userId: UserId): Boolean = this.userId == userId
}