package com.ritier.springr2dbcsample.shared.exception

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {

    // 클라이언트 에러 (4xx)
    BAD_REQUEST(400, "CMM_001", "잘못된 요청입니다"),
    UNAUTHORIZED(401, "CMM_002", "인증이 필요합니다"),
    FORBIDDEN(403, "CMM_003", "접근 권한이 없습니다"),
    NOT_FOUND(404, "CMM_004", "요청한 리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(405, "CMM_005", "허용되지 않은 HTTP 메서드입니다"),
    CONFLICT(409, "CMM_006", "리소스 충돌이 발생했습니다"),
    VALIDATION_FAILED(422, "CMM_007", "입력값 검증에 실패했습니다"),

    // 서버 에러 (5xx)
    INTERNAL_SERVER_ERROR(500, "CMM_100", "서버 내부 오류가 발생했습니다"),
    DATABASE_ERROR(500, "CMM_101", "데이터베이스 오류가 발생했습니다"),
    EXTERNAL_API_ERROR(502, "CMM_102", "외부 API 호출에 실패했습니다"),
    SERVICE_UNAVAILABLE(503, "CMM_103", "서비스를 사용할 수 없습니다"),


    // 데이터베이스 설정 검증 에러 (5xx)
    DB_CONFIG_HOST_EMPTY(500, "ERR_201", "데이터베이스 호스트가 비어있습니다"),
    DB_CONFIG_DATABASE_EMPTY(500, "ERR_202", "데이터베이스명이 비어있습니다"),
    DB_CONFIG_USERNAME_EMPTY(500, "ERR_203", "데이터베이스 사용자명이 비어있습니다"),
    DB_CONFIG_PORT_INVALID(500, "ERR_204", "데이터베이스 포트번호는 1 이상 65535 이하여야합니다"),
    DB_CONFIG_PASSWORD_EMPTY(500, "ERR_205", "데이터베이스 비밀번호가 비어있습니다"),
    DB_CONFIG_DRIVER_INVALID(500, "ERR_206", "지원하지 않는 데이터베이스 드라이버입니다"),
    DB_CONFIG_PROTOCOL_INVALID(500, "ERR_207", "잘못된 데이터베이스 프로토콜입니다"),

    // 서버 설정 검증 에러 (500)
    SERVER_CONFIG_HOST_EMPTY(500, "ERR_601", "서버 호스트가 비어있습니다"),
    SERVER_CONFIG_PORT_INVALID(500, "ERR_602", "서버 포트는 1 이상 65535 이하여야 합니다"),
    SERVER_CONFIG_HOST_INVALID(500, "ERR_603", "잘못된 서버 호스트 형식입니다"),
    SERVER_CONFIG_ROUTER_FUNCTION_MISSING(500, "ERR_604", "라우터 함수가 누락되었습니다"),
    SERVER_STARTUP_FAILED(500, "ERR_605", "서버 시작에 실패했습니다"),
    SERVER_CONFIG_THREAD_POOL_INVALID(500, "ERR_606", "스레드 풀 설정이 잘못되었습니다"),

    // Value Object 검증 에러 (400)
    USER_ID_INVALID(400, "VO_301", "사용자 ID는 0보다 커야 합니다"),
    POSTING_ID_INVALID(400, "VO_302", "게시글 ID는 0보다 커야 합니다"),
    POSTING_IMAGE_ID_INVALID(400, "VO_302", "게시글 ID는 0보다 커야 합니다"),
    COMMENT_ID_INVALID(400, "VO_303", "댓글 ID는 0보다 커야 합니다"),
    IMAGE_ID_INVALID(400, "VO_304", "이미지 ID는 0보다 커야 합니다"),
    EMAIL_EMPTY(400, "VO_305", "이메일은 비어있을 수 없습니다"),
    EMAIL_INVALID_FORMAT(400, "VO_306", "올바른 이메일 형식이 아닙니다"),
    POSTING_CONTENT_EMPTY(400, "VO_307", "게시글 내용은 비어있을 수 없습니다"),
    POSTING_CONTENT_TOO_LONG(400, "VO_308", "게시글은 2000자를 초과할 수 없습니다"),
    PROFILE_ID_INVALID(400, "VO_309", "프로필 이미지 ID는 0보다 커야 합니다"),
    USER_CREDENTIAL_ID_INVALID(400, "VO_310", "사용자 Credential ID는 0보다 커야 합니다"),

    // 도메인 모델 검증 에러 (400)
    COMMENT_CONTENT_EMPTY(400, "DOMAIN_401", "댓글 내용은 비어있을 수 없습니다"),
    COMMENT_CONTENT_TOO_LONG(400, "DOMAIN_402", "댓글은 500자를 초과할 수 없습니다"),
    IMAGE_URL_EMPTY(400, "DOMAIN_403", "이미지 URL은 비어있을 수 없습니다"),
    IMAGE_SIZE_INVALID(400, "DOMAIN_404", "이미지 크기는 0보다 커야 합니다"),
    USER_CREDENTIAL_EMAIL_EMPTY(400, "DOMAIN_405", "사용자 인증 정보의 이메일은 비어있을 수 없습니다"),
    USER_CREDENTIAL_EMAIL_INVALID(400, "DOMAIN_406", "사용자 인증 정보의 이메일 형식이 올바르지 않습니다"),
    USER_CREDENTIAL_PASSWORD_EMPTY(400, "DOMAIN_407", "사용자 인증 정보의 비밀번호는 비어있을 수 없습니다"),
    USER_CREDENTIAL_USER_ID_INVALID(400, "DOMAIN_408", "사용자 인증 정보의 사용자 ID는 0보다 커야 합니다"),

    // Auth 에러
    PASSWORD_HASHING_FAILED(400, "AUTH_001", "비밀번호 해싱에 실패했습니다."),
    TOKEN_GENERATION_FAILED(400, "AUTH_002", "인증 토큰 생성에 실패했습니다."),
    USER_ALREADY_EXISTS(400, "AUTH_003", "이미 존재하는 사용자입니다."),
    USER_NOT_FOUND(400, "AUTH_004", "존재하지 않는 사용자입니다."),
    USER_INACTIVE(400, "AUTH_005", "비활성화된 사용자입니다."),
    INVALID_PASSWORD(400, "AUTH_006", "유효하지 않은 비밀번호입니다."),
    INVALID_JWT_SECRET_KEY(400, "AUTH_007", "JWT의 시크릿 키는 64자 이상이어야 합니다."),
    INVALID_JWT_EXPIRATION(400, "AUTH_008", "JWT의 만료기한은 0 보다 커야 합니다."),
    INVALID_PASSWORD_HASH_ROUNDS(400, "AUTH_009", "BCrypt 해시 횟수는 4 과 31 사이의 수여야 합니다."),

    // Posting 에러
    POSTING_NOT_FOUND(400, "POST_001", "게시물을 찾을 수 없습니다."),

    // FIle(Image) 에러
    FILES_NOT_FOUND(400, "FILE_001", "파일을 찾을 수 없습니다."),
    INVALID_FILENAME(400, "FILE_002", "유효하지 않은 파일명입니다."),
    UNSUPPORTED_FILE_TYPE(400, "FILE_003", "지원하지 않는 파일 확장자입니다."),
    IMAGE_PROCESSING_FAILED(500, "FILE_004", "이미지 처리에 실패했습니다."),
    INVALID_IMAGE_FORMAT(400, "FILE_005", "유효하지 않은 이미지 확장자입니다."),
    FILENAME_TOO_LONG(400, "FILE_006", "파일명이 너무 깁니다."),
    INVALID_FILENAME_CHARS(400, "FILE_007", "파일명에 허용되지 않는 문자가 포함되어 있습니다"),
    INVALID_IMAGE_WIDTH(400, "FILE_008", "이미지 너비가 유효하지 않습니다."),
    INVALID_IMAGE_HEIGHT(400, "FILE_009", "이미지 높이가 유효하지 않습니다."),
    IMAGE_TOO_LARGE(400, "FILE_010", "이미지의 크기가 너무 큽니다."),
    INVALID_MIME_TYPE(400, "FILE_011", "파일 형식이 유효하지 않습니다."),
    UNSUPPORTED_IMAGE_TYPE(400, "FILE_012", "지원하지 않는 이미지 형식입니다."),
    NO_FILES_PROVIDED(400, "FILE_013", "업로드할 파일이 없습니다"),
    UPLOAD_FILES_SIZE_EXCEEDED(400, "FILE_014", "한 번에 업로드할 수 있는 파일 수를 초과했습니다"),

    // Repository 매핑 오류
    POSTING_DATA_CONVERSION_ERROR(500, "MAP_701", "게시글 데이터 변환 중 오류가 발생했습니다"),
    POSTING_QUERY_EXECUTION_ERROR(500, "MAP_702", "게시글 쿼리 실행 중 오류가 발생했습니다"),
    POSTING_IMAGE_MAPPING_ERROR(500, "MAP_703", "게시글 이미지 매핑 중 오류가 발생했습니다")

    ;

    companion object {
        fun fromCode(code: String): ErrorCode? {
            return values().find { it.code == code }
        }

        fun fromStatus(status: Int): List<ErrorCode> {
            return values().filter { it.status == status }
        }
    }
}