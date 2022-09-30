package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDateTime

@Table("posting_comments")
data class Comment(
    @Id
    @Column("comment_id") val id: Long,
    @Column("user_id") val userId: Long,
    @Column("posting_id") val postingId: Long,
    @Column("contents") val contents : String,
    @CreatedDate
    @Column("created_at") val createdAt: LocalDateTime,
//    @Transient
//    val user : User?,
//    @Transient
//    val posting : Posting?,
) {
    override fun toString(): String {
        return "Comment { id : $id, userId : ${userId.toString()}, contents : $contents, postingId : ${postingId.toString()} }"
    }
}
