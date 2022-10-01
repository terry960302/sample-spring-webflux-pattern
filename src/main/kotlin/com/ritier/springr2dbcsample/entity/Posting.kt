package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDateTime

@Table("postings")
data class Posting(
    @Id
    @Column("posting_id") val id: Long,
    @Column("user_id") val userId: Long,
    @Column("contents") val contents: String,
    @CreatedDate
    @Column("created_at") val createdAt: LocalDateTime,
    @Transient var user: User?,
    @Transient var images: List<Image>?,
    @Transient var comments: List<Comment>?,
) {
    override fun toString(): String {
        return "Posting { " +
                "id : $id, userId : $userId, " +
                "user : ${user.toString()}, " +
                "contents : $contents, " +
                "images : ${images?.map { it.toString() }}, " +
                "comments : ${comments?.map { it.toString() }}" +
                "createdAt : $createdAt }"
    }
}

