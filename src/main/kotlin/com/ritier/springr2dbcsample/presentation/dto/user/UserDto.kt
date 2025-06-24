package com.ritier.springr2dbcsample.presentation.dto.user

import com.ritier.springr2dbcsample.domain.model.User
import com.ritier.springr2dbcsample.domain.vo.user.Email
import com.ritier.springr2dbcsample.presentation.dto.image.ImageDto

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