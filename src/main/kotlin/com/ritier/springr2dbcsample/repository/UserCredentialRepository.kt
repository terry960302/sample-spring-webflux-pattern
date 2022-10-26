package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.UserCredential
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCredentialRepository : ReactiveCrudRepository<UserCredential, Long> {
    @Query("SELECT EXISTS (SELECT * FROM user_credentials WHERE email = :email);")
    suspend fun findUserByEmail(email : String) : UserCredential
}