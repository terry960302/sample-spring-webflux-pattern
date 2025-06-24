package com.ritier.springr2dbcsample.infrastructure.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("posting_images")
data class PostingImageEntity(
    @Id
    @Column("posting_image_id")
    val id: Long = 0,

    @Column("posting_id")
    val postingId: Long,

    @Column("image_id")
    val imageId: Long,

    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)