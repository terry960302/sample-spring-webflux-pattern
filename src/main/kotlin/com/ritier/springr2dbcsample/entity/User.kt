package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    @Column("user_id") val id: Long?,
    @Column("nickname") val nickname: String,
    @Column("age") val age: Int,
    @Column("profile_img_id") val profileImgId: Long?, // 1-1
    @Transient val profileImg: Image?,
) {
    override fun toString(): String {
        return "User{id = $id, nickname = $nickname, age = $age, profile_img_id : $${profileImg.toString()}}"
    }
}