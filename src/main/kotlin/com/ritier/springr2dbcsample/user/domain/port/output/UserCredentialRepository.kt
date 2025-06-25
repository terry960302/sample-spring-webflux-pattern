package com.ritier.springr2dbcsample.user.domain.port.output

import com.ritier.springr2dbcsample.user.domain.model.UserCredential
import com.ritier.springr2dbcsample.user.domain.vo.UserId

interface UserCredentialRepository {
    suspend fun save(credential: UserCredential): UserCredential
    suspend fun findByEmail(email: String): UserCredential?
    suspend fun updateLastLogin(userId: UserId): Boolean
}