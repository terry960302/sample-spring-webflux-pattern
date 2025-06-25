package com.ritier.springr2dbcsample.user.domain.port.input

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.user.application.command.UpdateUserProfileCommand
import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class UserProfileUpdateUseCase(
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: UpdateUserProfileCommand): User {
        // 기존 사용자 조회
        val originUser = userRepository.findById(UserId(command.userId))
            ?: throw AppException(ErrorCode.USER_NOT_FOUND)

        val updatedUser = originUser.updateProfile(command.username, command.age)
        val savedUser = userRepository.save(updatedUser)

        logger.info { "사용자 프로필 업데이트 UseCase 실행 완료: ${savedUser.id.value}" }
        return savedUser
    }
}