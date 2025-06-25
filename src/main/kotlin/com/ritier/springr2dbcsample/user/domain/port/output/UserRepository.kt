package com.ritier.springr2dbcsample.user.domain.port.output

import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun count() : Long
    suspend fun save(user: User): User
    suspend fun findById(id: UserId): User?
    suspend fun findByEmail(email: String): User?
    suspend fun findAll(): Flow<User>
    suspend fun findByUsername(username: String): Flow<User>
    suspend fun deleteById(id: UserId): Boolean
    suspend fun update(user: User): User
}
