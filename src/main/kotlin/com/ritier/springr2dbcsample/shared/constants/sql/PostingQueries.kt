package com.ritier.springr2dbcsample.shared.constants.sql

object PostingQueries {
    // ===== 컬럼 정의 =====
    private const val USER_COLUMNS = """
        u.user_id, 
        u.username, 
        u.age, 
        u.profile_img_id,
    """

    private const val PROFILE_IMG_COLUMNS = """
        i.url as profile_img_url,
        i.width as profile_img_width, 
        i.height as profile_img_height,
        i.file_name as profile_img_file_name,
        i.file_size as profile_img_file_size,
        i.mime_type as profile_img_mime_type,
        i.uploaded_at as profile_img_uploaded_at
    """

    private const val USER_PROFILE_COLUMNS = """
        $USER_COLUMNS,
        $PROFILE_IMG_COLUMNS
    """

    private const val POSTING_BASE_COLUMNS = """
        p.posting_id, 
        p.contents as posting_contents, 
        p.created_at as posting_created_at
    """

    private const val POSTING_IMAGE_COLUMNS = """
        pi_img.image_id as posting_img_id, 
        pi_img.url as posting_img_url,
        pi_img.width as posting_img_width, 
        pi_img.file_name as posting_img_file_name,
        pi_img.file_size as posting_img_file_size,
        pi_img.mime_type as posting_img_mime_type,
        pi_img.uploaded_at as posting_img_uploaded_at
    """

    // ===== 기본 쿼리 템플릿 =====
    private const val USER_WITH_PROFILE_TEMPLATE = """
        SELECT $USER_PROFILE_COLUMNS
        FROM users u 
        LEFT JOIN images i ON i.image_id = u.profile_img_id
    """

    private const val POSTING_BASE_JOINS = """
        FROM postings p
        INNER JOIN ($USER_WITH_PROFILE_TEMPLATE) u ON u.user_id = p.user_id
        INNER JOIN posting_images pi ON pi.posting_id = p.posting_id
        INNER JOIN images pi_img ON pi.image_id = pi_img.image_id
    """

    // ===== 개별 쿼리 정의 =====

    // 사용자 프로필 정보 조회 (독립적으로 사용 가능)
    const val FETCH_USER_WITH_PROFILE = USER_WITH_PROFILE_TEMPLATE

    // 전체 포스팅 조회 (기본 템플릿)
    private const val FETCH_POSTINGS_BASE = """
        SELECT u.*, $POSTING_BASE_COLUMNS, $POSTING_IMAGE_COLUMNS
        $POSTING_BASE_JOINS
    """

    // 모든 포스팅 조회
    val FETCH_ALL_POSTINGS = buildPostingQuery()

    // 특정 사용자의 포스팅 조회
    val FETCH_ALL_POSTINGS_BY_USER_ID = buildPostingQuery(
        whereClause = "u.user_id = :user_id"
    )

    // 단일 포스팅 조회
    val FETCH_SINGLE_POSTING = buildPostingQuery(
        whereClause = "p.posting_id = :postingId"
    )


    // 포스팅 쿼리 빌더
    private fun buildPostingQuery(
        whereClause: String = "",
        orderBy: String = "p.created_at DESC",
        limit: Int? = null
    ): String {
        val query = StringBuilder(FETCH_POSTINGS_BASE)

        if (whereClause.isNotBlank()) {
            query.append(" WHERE $whereClause")
        }

        if (orderBy.isNotBlank()) {
            query.append(" ORDER BY $orderBy")
        }

        if (limit != null && limit > 0) {
            query.append(" LIMIT $limit")
        }

        return query.toString().trimIndent()
    }
}