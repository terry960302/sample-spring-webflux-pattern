package com.ritier.springr2dbcsample.domain.model

import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.helper.PasswordHasher
import com.ritier.springr2dbcsample.domain.vo.user.Email
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import com.ritier.springr2dbcsample.infrastructure.entity.UserEntity
import java.time.LocalDateTime

data class User(
    val id: UserId,
    val username: String,
    val email: Email,
    val age: Int,
    val createdAt: LocalDateTime,
    val profileImg: Image? = null,
) {
    fun hasProfileImage(): Boolean = profileImg != null
    fun getProfileImageUrl(): String? = profileImg?.url

    // 엔티티로 변환
    fun toEntity(): UserEntity {
        return UserEntity(
            id = id.value,
            username = username,
            email = email.value,
            age = age,
            createdAt = createdAt,
            profileImgId = profileImg?.id,
        )
    }

    // 도메인 로직: 자격증명 생성 요청
    fun createCredential(rawPassword: String, passwordHasher: PasswordHasher): UserCredential {
        val hashedPassword = passwordHasher.hash(rawPassword)
        return UserCredential.createNew(
            userId = this.id,
            email = this.email.value,
            encryptedPassword = hashedPassword
        )
    }

    companion object {

        fun fromJoinResult(
            userRow: Map<String, Any>,
            profileImg: Image? = null
        ): User {
            return User(
                id = UserId(userRow["user_id"] as Long),
                username = userRow["username"] as String,
                email = Email(userRow["email"] as String),
                age = userRow["age"] as Int,
                createdAt = userRow["created_at"] as LocalDateTime,
                profileImg = profileImg
            )
        }

        fun fromEntity(entity: UserEntity, profileImg: Image?): User {
            return User(
                id = UserId(entity.id),
                username = entity.username,
                email = Email(entity.email),
                createdAt = entity.createdAt,
                age = entity.age,
                profileImg = profileImg
            )
        }

        // 회원가입용 팩토리 메서드
        fun create(username: String, email: Email, age: Int): User {
            require(username.isNotBlank()) { ErrorCode.VALIDATION_FAILED.message }
            require(email.value.isNotBlank()) { ErrorCode.EMAIL_EMPTY.message }

            return User(
                id = UserId(0), // 새 사용자는 0으로 시작
                username = username,
                email = email,
                createdAt = LocalDateTime.now(),
                profileImg = null,
                age = age
            )
        }
    }
}