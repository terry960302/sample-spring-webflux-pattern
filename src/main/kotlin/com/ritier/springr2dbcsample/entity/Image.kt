package com.ritier.springr2dbcsample.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import javax.annotation.Generated

@Table("images")
data class Image(
    @Column("id") val id: Long,
    @Column("url") val url: String,
    @Column("width") val width: Int,
    @Column("height") val height: Int,
    @CreatedDate
    @Column("createdat") val createdAt: Date,
) {
    override fun toString(): String {
        return "Image { id : $id, url : $url}"
    }
}
