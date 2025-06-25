package com.ritier.springr2dbcsample.posting.adapter.out.persistence.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("postings")
data class PostingEntity(
    @Id
    @Column("posting_id")
    val id: Long = 0,

    @Column("user_id")
    val userId: Long,

    @Column("contents")
    val contents: String,

    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
