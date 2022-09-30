package com.ritier.springr2dbcsample.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("posting_images")
data class PostingImage(
    @Id
    @Column("posting_image_id") val id: Long,
    @Column("posting_id") val postingId: Long,
    @Column("image_id") val imageId: Long,
    @Transient
    var posting : Posting?,
    @Transient
    var image : Image?,
) {
    override fun toString(): String {
        return "PostingImage{ id : $id, posting : ${posting.toString()}, image : ${image.toString()}}"
    }
}
