package com.ritier.springr2dbcsample.domain.model

import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
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
        require(contents.isNotBlank()) { "게시글 내용은 비어있을 수 없습니다" }
        require(contents.length <= 2000) { "게시글은 2000자를 초과할 수 없습니다" }
    }

    companion object {
        fun create(userId: UserId, contents: String, images: List<Image>): Posting {
            return Posting(
                id = PostingId(0L),
                userId = userId,
                contents = contents,
                createdAt = LocalDateTime.now(),
            )
        }

        fun create(id: PostingId, user: User?, userId: UserId, createdAt: LocalDateTime, contents: String): Posting {
            return Posting(
                id = id,
                userId = userId,
                user = user,
                contents = contents,
                createdAt = createdAt,
            )
        }
    }

    fun getCommentCount(): Int = comments.size
    fun getImageCount(): Int = images.size
    fun hasImages(): Boolean = images.isNotEmpty()
}