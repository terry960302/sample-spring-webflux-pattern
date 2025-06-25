package com.ritier.springr2dbcsample.user.application.service

import com.ritier.springr2dbcsample.user.domain.port.input.UserProfileImageChangeUseCase
import com.ritier.springr2dbcsample.user.domain.port.input.UserProfileUpdateUseCase
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.user.application.command.ChangeProfileImageCommand
import com.ritier.springr2dbcsample.user.application.command.UpdateUserProfileCommand
import com.ritier.springr2dbcsample.image.domain.model.Image
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import com.ritier.springr2dbcsample.user.adapter.`in`.web.dto.UserDto
import com.ritier.springr2dbcsample.user.adapter.`in`.web.dto.UpdateUserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userProfileUpdateUseCase: UserProfileUpdateUseCase,
    private val userProfileImageChangeUseCase: UserProfileImageChangeUseCase,
    private val userRepository: UserRepository // 단순 CRUD 작업용
) {
    private val logger = KotlinLogging.logger {}

    suspend fun updateUser(id: Long, userDto: UpdateUserDto): UserDto {
        val command = UpdateUserProfileCommand(
            userId = id,
            username = userDto.username,
            age = userDto.age
        )

        val updatedUser = userProfileUpdateUseCase.execute(command)
        logger.info { "사용자 업데이트 완료: ${updatedUser.id.value}" }

        return UserDto.from(updatedUser)
    }

    suspend fun changeProfileImage(userId: Long, newImage: Image): UserDto {
        val command = ChangeProfileImageCommand(
            userId = userId,
            newImage = newImage
        )

        val updatedUser = userProfileImageChangeUseCase.execute(command)
        logger.info { "프로필 이미지 변경 완료: ${updatedUser.id.value}" }

        return UserDto.from(updatedUser)
    }


    suspend fun createUser(userDto: UserDto): UserDto {
        val user = userDto.toDomain()
        val savedUser = userRepository.save(user)
        return UserDto.from(savedUser)
    }
    @Transactional(readOnly = true)
    suspend fun findAllUsers(): Flow<UserDto> =
        userRepository.findAll().map { UserDto.from(it) }

    @Transactional(readOnly = true)
    suspend fun findUserById(id: Long): UserDto {
        val user = userRepository.findById(UserId(id))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)
        return UserDto.from(user)
    }

    suspend fun deleteUser(id: Long): Boolean {
        return userRepository.deleteById(UserId(id))
    }

    @Transactional(readOnly = true)
    suspend fun findUsersByUsername(username: String): Flow<UserDto> =
        userRepository.findByUsername(username).map { UserDto.from(it) }

    @Transactional(readOnly = true)
    suspend fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }
}
