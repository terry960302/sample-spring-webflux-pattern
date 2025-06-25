package com.ritier.springr2dbcsample.user.domain.model

import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.posting.domain.model.Comment
import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.auth.domain.port.output.PasswordHasher
import com.ritier.springr2dbcsample.image.domain.vo.ValidationResult
import com.ritier.springr2dbcsample.user.domain.vo.Email
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.user.adapter.out.persistence.entity.UserEntity
import java.time.LocalDateTime

data class User(
    val id: UserId,
    val username: String,
    val email: Email,
    val age: Int,
    val createdAt: LocalDateTime,
    val profileImg: Image? = null,
) {

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
        private const val MIN_AGE = 1
        private const val MAX_AGE = 150
        private const val MIN_USERNAME_LENGTH = 2
        private const val MAX_USERNAME_LENGTH = 50

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

        private fun validateForCreation(username: String, age: Int) {
            require(username.isNotBlank()) { ErrorCode.VALIDATION_FAILED.message }
            require(username.length in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) {
                ErrorCode.VALIDATION_FAILED.message
            }
            require(age in MIN_AGE..MAX_AGE) { ErrorCode.VALIDATION_FAILED.message }
        }
    }

    // 프로필 업데이트

    fun updateProfile(newUsername: String, newAge: Int): User {
        validateProfileUpdate(newUsername, newAge)

        return copy(
            username = newUsername.trim(),
            age = newAge
        )
    }

    // 프로필 이미지 변경

    fun changeProfileImage(newImage: Image): User {
        val validationResult = newImage.validate()
        require(validationResult is ValidationResult.Valid) {
            ErrorCode.INVALID_IMAGE_FORMAT.message
        }

        return copy(profileImg = newImage)
    }

    // 프로필 이미지 제거
    fun removeProfileImage(): User = copy(profileImg = null)


    // 게시물 작성 가능 여부 - 추후 복잡한 규칙 추가 가능
    fun canCreatePosting(): Boolean = age >= 14 // 13세 이상만 게시물 작성 가능

    // 댓글 작성 가능 여부
    fun canCreateComment(): Boolean = age >= 14

    // 다른 사용자의 게시물 수정 가능 여부 (관리자 권한 등)
    fun canEditPosting(posting: Posting): Boolean = posting.userId == this.id

    // 다른 사용자의 댓글 삭제 가능 여부
    fun canDeleteComment(comment: Comment): Boolean = comment.userId == this.id

    fun hasProfileImage(): Boolean = profileImg != null
    fun getProfileImageUrl(): String? = profileImg?.url

    private fun validateProfileUpdate(username: String, age: Int) {
        require(username.isNotBlank()) { ErrorCode.VALIDATION_FAILED.message }
        require(username.length in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) {
            ErrorCode.VALIDATION_FAILED.message
        }
        require(age in MIN_AGE..MAX_AGE) { ErrorCode.VALIDATION_FAILED.message }
    }

    private fun validatePassword(rawPassword: String) {
        require(rawPassword.isNotBlank()) { ErrorCode.VALIDATION_FAILED.message }
    }

}