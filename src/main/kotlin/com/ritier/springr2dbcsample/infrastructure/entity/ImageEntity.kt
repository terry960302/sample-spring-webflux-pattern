package com.ritier.springr2dbcsample.infrastructure.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "images")
data class ImageEntity(
    @Id
    val id: Long = 0,

    @Column(value = "file_name")
    val fileName: String,

    @Column
    val width: Int,

    @Column
    val height: Int,

    @Column("file_size")
    val fileSize: Long,

    @Column("mime_type")
    val mimeType: String,

    @Column("uploaded_at")
    val uploadedAt: LocalDateTime,

    @Column
    val url: String?
)