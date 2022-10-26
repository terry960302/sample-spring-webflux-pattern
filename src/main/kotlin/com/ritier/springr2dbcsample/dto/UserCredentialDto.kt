package com.ritier.springr2dbcsample.dto

import com.ritier.springr2dbcsample.dto.common.Role
import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.entity.UserCredential
import org.springframework.data.relational.core.mapping.Column

class UserCredentialDto
    (
    val id: Long,
    val userId: Long,
    val email: String,
    val password: String,
    val role: Role,
) {
    companion object Mapper {
        fun from(userCredential: UserCredential): UserCredentialDto {
            return UserCredentialDto(
                id = userCredential.id,
                userId = userCredential.userId,
                email = userCredential.email,
                password = userCredential.password,
                role = userCredential.role,
            )
        }
    }
}

fun UserCredentialDto.toEntity(): UserCredential {
    return UserCredential(
        id = this.id,
        userId = this.userId,
        email = this.email,
        password = this.password,
        role = this.role,
    )
}