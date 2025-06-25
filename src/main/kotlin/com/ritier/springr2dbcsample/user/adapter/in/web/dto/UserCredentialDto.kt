package com.ritier.springr2dbcsample.user.adapter.`in`.web.dto

import com.ritier.springr2dbcsample.auth.domain.model.Role
import com.ritier.springr2dbcsample.user.domain.model.UserCredential


class UserCredentialDto
    (
    val id: Long,
    val userId: Long,
    val email: String,
    val password: String,
    val role: Role,
) {
    companion object {
        fun from(userCredential: UserCredential): UserCredentialDto {
            return UserCredentialDto(
                id = userCredential.id.value,
                userId = userCredential.userId.value,
                email = userCredential.email,
                password = userCredential.getPassword(),
                role = userCredential.role,
            )
        }
    }
}
