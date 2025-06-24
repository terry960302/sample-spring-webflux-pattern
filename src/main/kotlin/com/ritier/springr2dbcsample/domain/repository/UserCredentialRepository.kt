package com.ritier.springr2dbcsample.domain.repository

import com.ritier.springr2dbcsample.domain.model.UserCredential
import com.ritier.springr2dbcsample.domain.vo.user.UserId

interface UserCredentialRepository {
    suspend fun save(credential: UserCredential): UserCredential
    suspend fun findByEmail(email: String): UserCredential?
    suspend fun updateLastLogin(userId: UserId): Boolean
}