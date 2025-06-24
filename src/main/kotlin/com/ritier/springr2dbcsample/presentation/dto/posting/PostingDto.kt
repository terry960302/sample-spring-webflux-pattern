package com.ritier.springr2dbcsample.presentation.dto.posting

import com.ritier.springr2dbcsample.domain.model.Posting
import com.ritier.springr2dbcsample.presentation.dto.user.UserDto
import com.ritier.springr2dbcsample.presentation.dto.image.ImageDto
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