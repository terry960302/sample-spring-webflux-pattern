package com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.posting.domain.model.Comment
import com.ritier.springr2dbcsample.user.adapter.`in`.web.dto.UserDto
import java.time.LocalDateTime

data class CommentDto(
    val id: Long,
    val userId: Long,
    val user: UserDto?,
    val postingId: Long,
    val contents: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(comment: Comment): CommentDto {
            return CommentDto(
                id = comment.id.value,
                userId = comment.userId.value,
                user = comment.user?.let { UserDto.from(it) },
                postingId = comment.postingId.value,
                contents = comment.contents,
                createdAt = comment.createdAt
            )
        }
    }
}