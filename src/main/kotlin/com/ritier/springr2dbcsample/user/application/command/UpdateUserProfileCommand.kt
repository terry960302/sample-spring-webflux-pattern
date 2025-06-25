package com.ritier.springr2dbcsample.user.application.command

data class UpdateUserProfileCommand(
    val userId: Long,
    val username: String,
    val age: Int
)