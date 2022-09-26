package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date

@Table("posting_comments")
data class Comment(
    @Id
    @Column("id") val id: Int,
    @Column("userid") val user: User,
    @Column("postingid") val posting: Posting,
    @Column("contents") val contents : String,
    @Column("createdat") val createdAt: Date,
) {
    override fun toString(): String {
        return "Comment { id : $id, user : ${user.toString()}, contents : $contents, posting : ${posting.toString()} }"
    }
}
