package com.ritier.springr2dbcsample.dto

import com.ritier.springr2dbcsample.entity.User

data class UserDto(
    val id: Long,
    val nickname: String,
    val age: Int,
    val profileImg: ImageDto?
) {
    companion object Mapper {
        fun from(user: User): UserDto {
            return UserDto(
                id = user.id!!,
                nickname = user.nickname,
                age = user.age,
                profileImg = if (user.profileImg == null) null else ImageDto.from(user.profileImg),
            )
        }
    }
}

fun UserDto.toEntity(): User {
    return User(
        id = this.id,
        nickname = this.nickname,
        age = this.age,
        profileImgId = this.profileImg?.id,
        profileImg = this.profileImg?.toEntity(),
    )
}
