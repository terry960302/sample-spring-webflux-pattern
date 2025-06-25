package com.ritier.springr2dbcsample.auth.domain.port.input

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.auth.application.command.RegisterUserCommand
import com.ritier.springr2dbcsample.auth.domain.port.output.PasswordHasher
import com.ritier.springr2dbcsample.user.domain.model.User
import com.ritier.springr2dbcsample.user.domain.port.output.UserCredentialRepository
import com.ritier.springr2dbcsample.user.domain.port.output.UserRepository
import com.ritier.springr2dbcsample.user.domain.vo.Email
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class UserRegistrationUseCase(
    private val userRepository: UserRepository,
    private val userCredentialRepository: UserCredentialRepository,
    private val passwordHasher: PasswordHasher
) {
    private val logger = KotlinLogging.logger {}

    suspend fun execute(command: RegisterUserCommand): User {
        // 기존 AuthService의 중복 이메일 체크 로직
        if (userRepository.findByEmail(command.email) != null) {
            throw AppException(ErrorCode.USER_ALREADY_EXISTS)
        }

        // 기존 User.create() 로직 활용
        val user = User.create(command.username, Email(command.email), command.age)
        val savedUser = userRepository.save(user)

        // 기존 자격증명 생성 로직 활용
        val credential = savedUser.createCredential(command.password, passwordHasher)
        userCredentialRepository.save(credential)

        logger.info { "회원가입 UseCase 실행 완료: ${savedUser.email.value}" }
        return savedUser
    }
}
