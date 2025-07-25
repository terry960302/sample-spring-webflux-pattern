package com.ritier.springr2dbcsample.user.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.vo.Email
import com.ritier.springr2dbcsample.image.adapter.`in`.web.dto.ImageDto

data class UserDto(
    val id: Long,
    val username: String,
    val email: String,
    val age: Int,
    val profileImg: ImageDto?
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                id = user.id.value,
                username = user.username,
                age = user.age,
                email = user.email.value,
                profileImg = user.profileImg?.let { ImageDto.from(it) }
            )
        }
    }

    fun toDomain(): User {
        return User.create(
            username = username,
            email = Email(email),
            age = age,
        )
    }

}