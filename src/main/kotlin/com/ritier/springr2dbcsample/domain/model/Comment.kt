package com.ritier.springr2dbcsample.domain.model

import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.vo.posting.CommentId
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import java.time.LocalDateTime

data class Comment(
    val id: CommentId,
    val userId: UserId,
    val postingId: PostingId,
    val contents: String,
    val createdAt: LocalDateTime,
    val user: User? = null
) {
    companion object {
        private const val MAX_LENGTH = 500;
    }

    init {
        require(contents.isNotBlank()) { ErrorCode.COMMENT_CONTENT_EMPTY.message }
        require(contents.length <= MAX_LENGTH) { ErrorCode.COMMENT_CONTENT_TOO_LONG.message }
    }

    fun isOwnedBy(userId: UserId): Boolean = this.userId == userId
}