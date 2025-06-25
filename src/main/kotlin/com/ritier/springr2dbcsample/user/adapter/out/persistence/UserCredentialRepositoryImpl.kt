package com.ritier.springr2dbcsample.user.adapter.out.persistence

import com.ritier.springr2dbcsample.user.domain.model.UserCredential
import com.ritier.springr2dbcsample.user.domain.port.output.UserCredentialRepository
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserCredentialRepositoryImpl(
    private val crudRepository: UserCredentialCrudRepository
) : UserCredentialRepository {

    override suspend fun findByEmail(email: String): UserCredential? {
        val entity = crudRepository.findUserByEmail(email)
        return UserCredential.fromEntity(entity)
    }

    override suspend fun updateLastLogin(userId: UserId): Boolean {
        val rowsAffected = crudRepository.updateLastLoginByUserId(
            userId = userId.value,
            lastLoginAt = LocalDateTime.now()
        )
        return rowsAffected > 0
    }

    override suspend fun save(userCredential: UserCredential): UserCredential {
        val entity = userCredential.toEntity()
        val savedEntity = crudRepository.save(entity).awaitSingle()
        return UserCredential.fromEntity(savedEntity)
    }
}
