package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.repository.UserRepository
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import com.ritier.springr2dbcsample.presentation.dto.user.UserDto
import com.ritier.springr2dbcsample.presentation.dto.user.UpdateUserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    suspend fun createUser(userDto: UserDto): UserDto {
        val user = userDto.toDomain()
        val savedUser = userRepository.save(user)
        return UserDto.from(savedUser)
    }

    suspend fun findAllUsers(): Flow<UserDto> =
        userRepository.findAll().map { UserDto.from(it) }

    suspend fun findUserById(id: Long): UserDto {
        val user = userRepository.findById(UserId(id))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)
        return UserDto.from(user)
    }

    suspend fun updateUser(id: Long, userDto: UpdateUserDto): UserDto {
        val originUser = userRepository.findById(UserId(id))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        val updatedUser = originUser.copy(
            username = userDto.username,
            age = userDto.age,
        )

        val savedUser = userRepository.save(updatedUser)
        return UserDto.from(savedUser)
    }

    suspend fun deleteUser(id: Long): Boolean {
        return userRepository.deleteById(UserId(id))
    }

    suspend fun findUsersByUsername(username: String): Flow<UserDto> =
        userRepository.findByUsername(username).map { UserDto.from(it) }

    suspend fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }
}
