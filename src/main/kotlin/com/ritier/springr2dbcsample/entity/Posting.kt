package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date

@Table("postings")
data class Posting(
    @Id
    @Column("posting_id") val id: Long,
    @Column("user_id") val user: User,
    @Column("contents") val contents: String,
    @Column("created_at") val createdAt: String,
) {
    override fun toString(): String {
        return "Posting { id : $id, user : ${user.toString()}, contents : $contents}, createdAt : $createdAt"
    }
}

