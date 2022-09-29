package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.entity.mapper.PostingMapper
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOne
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class PostingRepository {

    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @Autowired
    private lateinit var postingMapper: PostingMapper

    suspend fun findAll(): Flow<Posting> =
        databaseClient.sql(
            "SELECT * FROM postings AS p" +
                    "INNER JOIN users AS u ON u.user_id = p.posting_id" +
                    "INNER JOIN posting_images AS pi ON pi.posting_id = p.posting_id" +
                    "INNER JOIN images AS i ON i.image_id = pi.image_id" +
                    "INNER JOIN posting_comments AS c ON c.posting_id = p.posting_id"
        ).map(postingMapper::apply).flow()
}