package com.ritier.springr2dbcsample.user.adapter.`in`.web

import com.ritier.springr2dbcsample.posting.application.service.PostingService
import com.ritier.springr2dbcsample.user.adapter.`in`.web.dto.UserDto
import com.ritier.springr2dbcsample.user.application.service.UserService
import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.user.adapter.`in`.web.dto.UpdateUserDto
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*
import java.net.URI

@Component
class UserHandler(
    private val userService: UserService,
    private val postingService: PostingService
) {

    private val logger = KotlinLogging.logger {}

    suspend fun createUser(request: ServerRequest): ServerResponse {
        // 🟢 Global Error Handler가 처리하므로 try-catch 제거
        val userDto = request.awaitBodyOrNull<UserDto>()
            ?: throw AppException(ErrorCode.VALIDATION_FAILED)

        val createdUser = userService.createUser(userDto)

        logger.info { "사용자 생성 완료: userId=${createdUser.id}" }

        return ServerResponse.created(
            URI.create("/api/v1/users/${createdUser.id}")
        ).contentType(APPLICATION_JSON).bodyValueAndAwait(createdUser)
    }

    suspend fun updateUser(request: ServerRequest): ServerResponse {
        val userId = extractUserId(request)
        val updateDto = request.awaitBodyOrNull<UpdateUserDto>()
            ?: throw AppException(ErrorCode.VALIDATION_FAILED)

        val updatedUser = userService.updateUser(userId, updateDto)

        logger.info { "사용자 수정 완료: userId=$userId" }

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValueAndAwait(updatedUser)
    }

    suspend fun deleteUser(request: ServerRequest): ServerResponse {
        val userId = extractUserId(request)
        val deleted = userService.deleteUser(userId)

        if (!deleted) {
            throw AppException(ErrorCode.USER_NOT_FOUND)
        }

        logger.info { "사용자 삭제 완료: userId=$userId" }

        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun getUsers(request: ServerRequest): ServerResponse {
        val users = when (val username = request.queryParamOrNull("username")) {
            null -> userService.findAllUsers()
            else -> userService.findUsersByUsername(username)
        }

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyAndAwait(users)
    }

    suspend fun getUserById(request: ServerRequest): ServerResponse {
        val userId = extractUserId(request)
        val user = userService.findUserById(userId)

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValueAndAwait(user)
    }

    suspend fun getUserPostings(request: ServerRequest): ServerResponse {
        val userId = extractUserId(request)
        val postings = postingService.findPostingsByUserId(userId)

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyAndAwait(postings)
    }

    suspend fun checkEmailExists(request: ServerRequest): ServerResponse {
        val email = request.queryParam("email").orElse(null)
            ?: throw AppException(ErrorCode.EMAIL_EMPTY)

        val exists = userService.existsByEmail(email)

        return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValueAndAwait(mapOf("exists" to exists))
    }

    private fun extractUserId(request: ServerRequest): Long {
        return request.pathVariable("id").toLongOrNull()
            ?: throw AppException(ErrorCode.USER_ID_INVALID)
    }

}