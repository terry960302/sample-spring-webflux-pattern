package com.ritier.springr2dbcsample.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    @Column("id") val id: Long,
    @Column("nickname") val nickname: String,
    @Column("age") val age: Int,
    @Column("profileImgid") val profileImg: Image, // 1-1
) {
    override fun toString(): String {
        return "User{id = $id, nickname = $nickname, age = $age, profileImg : $${profileImg.toString()}}"
    }
}