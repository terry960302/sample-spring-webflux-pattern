package com.ritier.springr2dbcsample.domain.model

import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.helper.PasswordHasher
import com.ritier.springr2dbcsample.domain.vo.auth.LoginResult
import com.ritier.springr2dbcsample.domain.vo.user.UserCredentialId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import com.ritier.springr2dbcsample.infrastructure.entity.UserCredentialEntity
import com.ritier.springr2dbcsample.infrastructure.security.JwtTokenProvider
import java.time.LocalDateTime

data class UserCredential(
    val id: UserCredentialId,
    val userId: UserId,
    val role: Role,
    val email: String,
    private val encryptedPassword: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val lastLoginAt: LocalDateTime? = null,
    val isActive: Boolean = true
) {
    init {
        require(email.isNotBlank()) { ErrorCode.USER_CREDENTIAL_EMAIL_EMPTY.message }
        require(isValidEmail(email)) { ErrorCode.USER_CREDENTIAL_EMAIL_INVALID.message }
        require(encryptedPassword.isNotBlank()) { ErrorCode.USER_CREDENTIAL_PASSWORD_EMPTY.message }
        require(userId.value > 0) { ErrorCode.USER_CREDENTIAL_USER_ID_INVALID.message }
    }

    fun getPassword(): String = encryptedPassword

    fun isEmailMatches(email: String): Boolean = this.email.equals(email, ignoreCase = true)

    fun hasRole(requiredRole: Role): Boolean = role.hasAuthority(requiredRole)

    fun isAdmin(): Boolean = role == Role.ADMIN

    fun withLastLogin(loginTime: LocalDateTime): UserCredential {
        return copy(lastLoginAt = loginTime)
    }

    fun deactivate(): UserCredential = copy(isActive = false)

    fun changeRole(newRole: Role): UserCredential = copy(role = newRole)

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }

    override fun toString(): String {
        return "UserCredential(id=$id, userId=$userId, role=$role, email=$email, isActive=$isActive)"
    }

    fun issueToken(tokenProvider: JwtTokenProvider): String =
        tokenProvider.generateToken(userId)



    // 도메인 로직: 스스로 로그인 시도
    fun attemptLogin(rawPassword: String, passwordHasher: PasswordHasher): LoginResult {
        return when {
            !isActive -> LoginResult.AccountInactive
            !passwordHasher.matches(rawPassword, encryptedPassword) -> LoginResult.WrongPassword
            else -> LoginResult.Success(this.copy(lastLoginAt = LocalDateTime.now()))
        }
    }

    // 도메인 로직: 토큰 생성 권한 확인
    fun canGenerateToken(): Boolean = isActive && role != Role.BANNED

    // 엔티티로 변환
    fun toEntity(): UserCredentialEntity {
        return UserCredentialEntity(
            id = id.value,
            userId = userId.value,
            role = role,
            email = email,
            password = encryptedPassword,
            createdAt = createdAt,
            lastLoginAt = lastLoginAt,
            isActive = isActive
        )
    }

    companion object {
        fun fromEntity(entity: UserCredentialEntity): UserCredential {
            return UserCredential(
                id = UserCredentialId(entity.id),
                userId = UserId(entity.userId),
                role = entity.role,
                email = entity.email,
                encryptedPassword = entity.password,
                createdAt = entity.createdAt,
                lastLoginAt = entity.lastLoginAt,
                isActive = entity.isActive
            )
        }

        fun createNew(
            userId: UserId,
            email: String,
            encryptedPassword: String,
            role: Role = Role.USER
        ): UserCredential {
            return UserCredential(
                id = UserCredentialId(0), // 새 자격증명은 0으로 시작
                userId = userId,
                role = role,
                email = email,
                encryptedPassword = encryptedPassword
            )
        }
    }
}