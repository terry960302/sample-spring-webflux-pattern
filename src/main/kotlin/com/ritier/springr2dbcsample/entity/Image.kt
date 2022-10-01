package com.ritier.springr2dbcsample.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDateTime
import javax.annotation.Generated

@Table("images")
data class Image(
    @Id
    @Column("image_id") val id: Long,
    @Column("url") val url: String,
    @Column("width") val width: Int,
    @Column("height") val height: Int,
    @CreatedDate
    @Column("created_at") val createdAt: LocalDateTime,
) {
    override fun toString(): String {
        return "Image { id : $id, url : $url, created_at : $createdAt}"
    }
}
