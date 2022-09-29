package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.dto.UserDto
import com.ritier.springr2dbcsample.dto.toEntity
import com.ritier.springr2dbcsample.repository.UserRepository
import com.ritier.springr2dbcsample.entity.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun createUser(user: UserDto): UserDto = UserDto.from(userRepository.save(user.toEntity()))

    suspend fun findAllUsers(): Flow<UserDto> = userRepository.findAll().map { UserDto.from(it) }

    suspend fun findUserById(id: Long): UserDto = UserDto.from(userRepository.findById(id))

    suspend fun findUsersByNickname(nickname: String): Flow<UserDto> = userRepository.findByNickname(nickname).map { UserDto.from(it) }

    suspend fun updateUser(id: Long, user: User): UserDto = withContext(Dispatchers.IO) {
        val originUser = userRepository.findById(id)
        val updatedUser = userRepository.save(
            originUser.copy(
                nickname = user.nickname,
                age = user.age,
                profileImgId = user.profileImgId,
            )
        )
        UserDto.from(updatedUser)
    }

    suspend fun deleteUser(id: Long): Unit = userRepository.deleteById(id)
}