package com.ritier.springr2dbcsample.domain.model

enum class Role(val authority: String, val description: String) {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    MODERATOR("ROLE_MODERATOR", "운영자"),
    BANNED("ROLE_BANNED", "차단된 사용자"),
    ;

    fun hasAuthority(requiredRole: Role): Boolean {
        return when (this) {
            ADMIN -> true // 관리자는 모든 권한
            MODERATOR -> requiredRole != ADMIN // 운영자는 관리자 권한 제외 모든 권한
            USER -> requiredRole == USER // 일반 사용자는 사용자 권한만
            BANNED -> false // 차단된 사용자는 모두 불가
        }
    }
}