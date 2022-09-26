package com.ritier.springr2dbcsample.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("posting_images")
data class PostingImage(
    @Id
    @Column("id") val id: Long,
    @Column("postingid") val posting: Posting,
    @Column("imageid") val image: Image,
) {
    override fun toString(): String {
        return "PostingImage{ id : $id, posting : ${posting.toString()}, image : ${image.toString()}}"
    }
}
