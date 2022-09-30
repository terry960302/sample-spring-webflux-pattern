package com.ritier.springr2dbcsample.dto

import com.ritier.springr2dbcsample.entity.Comment
import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.entity.User
import org.springframework.data.relational.core.mapping.Column
import java.sql.Date
import java.time.LocalDateTime

data class PostingDto(
    val id: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val user: User?,
    val images: List<ImageDto>?,
    val comments: List<CommentDto>?,
) {
    companion object Mapper {
        fun from(posting: Posting): PostingDto {
            return PostingDto(
                id = posting.id,
                user = posting.user,
                contents = posting.contents,
                createdAt = posting.createdAt,
                images = posting.images?.map { ImageDto.from(it) },
                comments = posting.comments?.map { CommentDto.from(it) },
            )
        }
    }
}

fun PostingDto.toEntity(): Posting {
    return Posting(
        id = this.id,
        userId = this.user!!.id,
        user = this.user,
        contents = this.contents,
        createdAt = this.createdAt,
        images = this.images?.map { it.toEntity() },
        comments = this.comments?.map { it.toEntity() },
    )
}
