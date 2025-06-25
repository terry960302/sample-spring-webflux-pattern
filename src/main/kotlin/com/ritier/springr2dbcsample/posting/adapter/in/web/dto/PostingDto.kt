package com.ritier.springr2dbcsample.posting.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.user.adapter.`in`.web.dto.UserDto
import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageDto
import java.time.LocalDateTime

data class PostingDto(
    val id: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val user: UserDto?,
    val images: List<ImageDto>?,
    val comments: List<CommentDto>?,
) {
    companion object Mapper {
        fun from(posting: Posting): PostingDto {
            return PostingDto(
                id = posting.id.value,
                user = if (posting.user == null) null else UserDto.from(posting.user),
                contents = posting.contents,
                createdAt = posting.createdAt,
                images = posting.images.map { ImageDto.from(it) },
                comments = posting.comments.map { CommentDto.from(it) },
            )
        }
    }
}