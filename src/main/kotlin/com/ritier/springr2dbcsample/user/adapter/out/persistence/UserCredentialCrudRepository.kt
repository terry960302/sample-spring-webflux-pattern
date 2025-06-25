package com.ritier.springr2dbcsample.user.adapter.out.persistence

import com.ritier.springr2dbcsample.user.adapter.out.persistence.entity.UserCredentialEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

// spring 과 kotlin internal로 충돌로 인해 잠시 internal은 보류
@Repository
interface UserCredentialCrudRepository : ReactiveCrudRepository<UserCredentialEntity, Long> {
    @Query("SELECT * FROM user_credentials WHERE email = :email")
    suspend fun findUserByEmail(email : String) : UserCredentialEntity

    @Query("UPDATE user_credentials SET last_login_at = :lastLoginAt, updated_at = :updatedAt WHERE user_id = :userId")
    @Modifying
    suspend fun updateLastLoginByUserId(
        userId: Long,
        lastLoginAt: LocalDateTime,
        updatedAt: LocalDateTime = LocalDateTime.now()
    ): Int
}