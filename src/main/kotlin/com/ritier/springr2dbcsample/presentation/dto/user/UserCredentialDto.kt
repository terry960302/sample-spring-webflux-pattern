package com.ritier.springr2dbcsample.presentation.dto.user

import com.ritier.springr2dbcsample.domain.model.Role
import com.ritier.springr2dbcsample.domain.model.UserCredential


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
