package com.ritier.springr2dbcsample.infrastructure.entity

import com.ritier.springr2dbcsample.domain.vo.image.ImageId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class UserEntity(
    @Id
    @Column("user_id")
    val id: Long = 0,

    @Column("username")
    val username: String,

    @Column("age")
    val age : Int,

    @Column("email")
    val email: String,

    @Column("profile_img_id")
    val profileImgId : ImageId?,

    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)