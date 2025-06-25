package com.ritier.springr2dbcsample.shared.constants.sql

object PostingImageQueries {
    const val FIND_ALL_BY_POSTING_ID =
        """SELECT 
            * 
            FROM posting_images as pi 
            JOIN images as i ON i.image_id = pi.image_id 
            WHERE pi.posting_id = :postingId
            ;
        """;
}