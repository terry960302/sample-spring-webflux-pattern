package com.ritier.springr2dbcsample.common.constants.sql

object UserQueries {

    // ===== 기본 컬럼 정의 =====
    private const val USER_BASE_COLUMNS = """
        u.user_id, 
        u.username, 
        u.email, 
        u.age, 
        u.created_at
    """

    private const val PROFILE_IMAGE_JOIN_COLUMNS = """
        i.image_id as profile_img_id, 
        i.url as profile_img_url,
        i.width as profile_img_width, 
        i.height as profile_img_height,
        i.file_name as profile_img_file_name,
        i.file_size as profile_img_file_size,
        i.mime_type as profile_img_mime_type,
        i.uploaded_at as profile_img_uploaded_at
    """

    private const val ALL_USER_COLUMNS = "$USER_BASE_COLUMNS, $PROFILE_IMAGE_JOIN_COLUMNS"

    // ===== FROM 절 정의 =====
    private const val USER_BASE_FROM = "FROM users u"

    private const val USER_WITH_IMAGE_FROM = """
        FROM users u 
        LEFT JOIN images i ON i.image_id = u.profile_img_id
    """

    // ===== SELECT 쿼리 템플릿 =====
    private fun selectUsersWithImage(whereClause: String = "", orderBy: String = ""): String {
        val query = StringBuilder("SELECT $ALL_USER_COLUMNS $USER_WITH_IMAGE_FROM")

        if (whereClause.isNotBlank()) {
            query.append(" WHERE $whereClause")
        }

        if (orderBy.isNotBlank()) {
            query.append(" ORDER BY $orderBy")
        }

        return query.toString().trimIndent()
    }

    // ===== 개별 쿼리 정의 =====

    const val COUNT_USERS = "SELECT COUNT(*) AS count $USER_BASE_FROM"
    const val EXISTS_USER_BY_EMAIL = "SELECT COUNT(*) AS count $USER_BASE_FROM WHERE email = :email"
    const val INSERT_USER = """
        INSERT INTO users (username, email, age, created_at) 
        VALUES (:username, :email, :age, :createdAt) 
        RETURNING user_id
    """
    const val UPDATE_USER = """
        UPDATE users 
        SET username = :username, email = :email, age = :age 
        WHERE user_id = :id 
        RETURNING *
    """
    const val DELETE_USER_BY_ID = "DELETE $USER_BASE_FROM WHERE user_id = :id"
    val SELECT_USER_BY_ID = selectUsersWithImage(
        whereClause = "u.user_id = :id"
    )
    val SELECT_USER_BY_EMAIL = selectUsersWithImage(
        whereClause = "u.email = :email"
    )
    val SELECT_ALL_USERS = selectUsersWithImage(
        orderBy = "u.created_at DESC"
    )
    val SELECT_USERS_BY_USERNAME = selectUsersWithImage(
        whereClause = "u.username LIKE :username"
    )
    val SELECT_USERS_BY_IDS = selectUsersWithImage(
        whereClause = "u.user_id = ANY(:userIds)"
    )
}
//    const val COUNT_USERS = "SELECT COUNT(*) AS count FROM users"
//
//    const val INSERT_USER = """
//        INSERT INTO users (username, email, age, created_at)
//        VALUES (:username, :email, :age, :createdAt)
//        RETURNING user_id
//    """
//
//    const val SELECT_USER_BY_ID = """
//        SELECT
//            u.user_id,
//            u.username,
//            u.email,
//            u.age,
//            u.created_at,
//            i.image_id as profile_img_id,
//            i.url as profile_img_url,
//            i.width as profile_img_width,
//            i.height as profile_img_height,
//            i.created_at as profile_img_created_at
//        FROM users u
//        LEFT JOIN images i ON i.image_id = u.profile_img_id
//        WHERE u.user_id = :id
//    """
//
//    const val SELECT_USER_BY_EMAIL = """
//        SELECT
//            u.user_id,
//            u.username,
//            u.email,
//            u.age,
//            u.created_at,
//            i.image_id as profile_img_id,
//            i.url as profile_img_url,
//            i.width as profile_img_width,
//            i.height as profile_img_height,
//            i.created_at as profile_img_created_at
//        FROM users u
//        LEFT JOIN images i ON i.image_id = u.profile_img_id
//        WHERE u.email = :email
//    """
//
//    const val SELECT_ALL_USERS = """
//        SELECT
//            u.user_id,
//            u.username,
//            u.email,
//            u.age,
//            u.created_at,
//            i.image_id as profile_img_id,
//            i.url as profile_img_url,
//            i.width as profile_img_width,
//            i.height as profile_img_height,
//            i.created_at as profile_img_created_at
//        FROM users u
//        LEFT JOIN images i ON i.image_id = u.profile_img_id
//        ORDER BY u.created_at DESC
//    """
//
//    const val SELECT_USERS_BY_USERNAME = """
//        SELECT
//            u.user_id,
//            u.username,
//            u.email,
//            u.age,
//            u.created_at,
//            i.image_id as profile_img_id,
//            i.url as profile_img_url,
//            i.width as profile_img_width,
//            i.height as profile_img_height,
//            i.created_at as profile_img_created_at
//        FROM users u
//        LEFT JOIN images i ON i.image_id = u.profile_img_id
//        WHERE u.username LIKE :username
//    """
//
//    const val DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = :id"
//
//    const val UPDATE_USER = """
//        UPDATE users
//        SET username = :username, email = :email, age = :age
//        WHERE user_id = :id
//        RETURNING *
//    """
//
//    const val SELECT_USERS_BY_IDS = """
//        SELECT
//            u.user_id, u.username, u.email, u.age, u.created_at,
//            i.image_id as profile_img_id, i.url as profile_img_url,
//            i.width as profile_img_width, i.height as profile_img_height,
//            i.created_at as profile_img_created_at
//        FROM users u
//        LEFT JOIN images i ON i.image_id = u.profile_img_id
//        WHERE u.user_id = ANY(:userIds)
//    """
//
//    const val EXISTS_USER_BY_EMAIL = "SELECT COUNT(*) AS count FROM users WHERE email = :email"
//    }
