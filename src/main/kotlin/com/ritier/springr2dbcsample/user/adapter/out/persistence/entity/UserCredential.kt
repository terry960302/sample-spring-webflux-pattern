package com.ritier.springr2dbcsample.user.adapter.out.persistence.entity

import com.ritier.springr2dbcsample.auth.domain.model.Role
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user_credentials")
data class UserCredentialEntity(
    @Id
    @Column("user_credential_id")
    val id: Long = 0,

    @Column("user_id")
    val userId: Long,

    @Column("role")
    val role: Role,

    @Column("email")
    val email: String,

    @Column("password")
    val password: String,

    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column("last_login_at")
    val lastLoginAt: LocalDateTime? = null,

    @Column("is_active")
    val isActive: Boolean = true
)