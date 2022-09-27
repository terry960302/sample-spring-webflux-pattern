package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.repository.UserRepository
import com.ritier.springr2dbcsample.entity.User
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun createUser(user: User): User = userRepository.save(user)

    suspend fun findAllUsers(): Flow<User> = userRepository.findAll()

    suspend fun findUserById(id: Long): User = userRepository.findById(id);

    suspend fun findUsersByNickname(nickname: String): Flow<User> = userRepository.findByNickname(nickname)

    suspend fun updateUser(id: Long, user: User): User {
        val originUser = userRepository.findById(id)
        return userRepository.save(
            originUser.copy(
                nickname = user.nickname,
                age = user.age,
                profileImgId = user.profileImgId,
            )
        )
    }
    suspend fun deleteUser(id: Long): Unit = userRepository.deleteById(id)
}