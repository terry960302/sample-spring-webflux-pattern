package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.dto.UserCredentialDto
import com.ritier.springr2dbcsample.dto.UserDto
import com.ritier.springr2dbcsample.dto.auth.AuthResponse
import com.ritier.springr2dbcsample.dto.auth.SignUpRequest
import com.ritier.springr2dbcsample.dto.common.Role
import com.ritier.springr2dbcsample.dto.toEntity
import com.ritier.springr2dbcsample.repository.UserRepository
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.entity.UserCredential
import com.ritier.springr2dbcsample.repository.UserCredentialRepository
import com.ritier.springr2dbcsample.security.SecurityProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val userCredentialRepository: UserCredentialRepository,
    val securityProvider: SecurityProvider
) {

    suspend fun signUp(req: SignUpRequest) = withContext(Dispatchers.IO) {
        val hasUser = findUserByEmail(req.email) != null
        if(hasUser) throw Error("There's a user matching this email(${req.email})")

        val userAsync = async {
            userRepository.save(
                User(
                    id = null,
                    nickname = req.nickname,
                    age = req.age,
                    profileImgId = null,
                    profileImg = null,
                )
            )
        }

        val user = userAsync.await()
        userCredentialRepository.save(
            UserCredential(
                id = null,
                email = req.email,
                password = securityProvider.hashPassword(req.password),
                role = Role.ROLE_USER,
                userId = userAsync.await().id
            )
        )
        val token = securityProvider.generateToken(user.id!!)
        AuthResponse(token)
    }

    suspend fun signIn(email: String, password: String): AuthResponse {
        val user = this.findUserByEmail(email) ?: throw Error("There's no user from matching email($email)")
        val hashedPassword = securityProvider.hashPassword(password)
        val isMatchPassword = user.password == hashedPassword
        if (!isMatchPassword) throw Error("There's no user from matching password")

        val token = securityProvider.generateToken(user.userId)
        return AuthResponse(token)
    }


    suspend fun findUserByEmail(email: String): UserCredentialDto? =
        UserCredentialDto.from(userCredentialRepository.findUserByEmail(email))

    suspend fun createUser(user: UserDto): UserDto = UserDto.from(userRepository.save(user.toEntity()))

    suspend fun findAllUsers(): Flow<UserDto> = userRepository.findAll().map { UserDto.from(it) }

    suspend fun findUserById(id: Long): UserDto = UserDto.from(userRepository.findById(id))

    suspend fun findUsersByNickname(nickname: String): Flow<UserDto> =
        userRepository.findByNickname(nickname).map { UserDto.from(it) }

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