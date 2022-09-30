package com.ritier.springr2dbcsample.dto

import com.ritier.springr2dbcsample.entity.Comment
import com.ritier.springr2dbcsample.entity.Posting
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class CommentDto(
    val id: Long,
    val userId: Long,
    val postingId: Long,
    val contents: String,
    val createdAt: LocalDateTime,
){
    companion object Mapper {
        fun from(comment: Comment): CommentDto {
            return CommentDto(
                id = comment.id,
                userId = comment.userId,
                postingId = comment.postingId,
                contents = comment.contents,
                createdAt = comment.createdAt,
            )
        }
    }
}

fun CommentDto.toEntity(): Comment {
    return Comment(
        id = this.id,
        userId = this.userId,
        postingId = this.postingId,
        contents = this.contents,
        createdAt = this.createdAt,
    )
}