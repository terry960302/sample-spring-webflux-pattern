package com.ritier.springr2dbcsample.common.constants.sql

object CommentQueries {
    const val FIND_ALL_BY_POSTING_ID = """
        SELECT 
            c.* FROM posting_comments as c 
        WHERE c.posting_id = :postingId
        ;
    """
}