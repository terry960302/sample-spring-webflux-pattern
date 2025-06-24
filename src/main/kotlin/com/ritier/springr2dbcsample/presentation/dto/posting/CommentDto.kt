package com.ritier.springr2dbcsample.presentation.dto.posting

import com.ritier.springr2dbcsample.domain.model.Comment
import com.ritier.springr2dbcsample.presentation.dto.user.UserDto
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