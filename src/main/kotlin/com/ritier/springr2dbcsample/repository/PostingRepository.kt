package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.entity.mapper.PostingMapper
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOne
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
interface PostingRepository : ReactiveCrudRepository<Posting, Long> {
}

// blocking issue 떄문에 join 후 aggregate 는 보류
//@Repository
//class PostingRepository {
//
//    @Autowired
//    private lateinit var databaseClient: DatabaseClient
//
//    @Autowired
//    private lateinit var postingMapper: PostingMapper
//
//    suspend fun findAll(): Flow<Posting> =
//        databaseClient.sql(
//            "SELECT" +
//                    "u.user_id as user_id," +
//                    "u.nickname as user_nickname," +
//                    "u.age as user_age," +
//                    "u.profile_img_id as user_profile_img_id," +
//                    "p.posting_id as posting_id," +
//                    "p.contents as posting_contents," +
//                    "p.created_at as posting_created_at," +
//                    "i.image_id as image_id," +
//                    "i.url as image_url," +
//                    "i.width as image_width," +
//                    "i.height as image_height," +
//                    "c.comment_id as comment_id," +
//                    "c.contents as comment_contents," +
//                    "c.created_at as comment_created_at" +
//                    "FROM postings AS p" +
//                    "INNER JOIN users AS u ON u.user_id = p.posting_id" +
//                    "INNER JOIN posting_images AS pi ON pi.posting_id = p.posting_id" +
//                    "INNER JOIN images AS i ON i.image_id = pi.image_id" +
//                    "INNER JOIN posting_comments AS c ON c.posting_id = p.posting_id"
//        ).fetch().all().bufferUntilChanged<String>{ it["posting_id"].toString() }.map{it -> it.}.flow()
//}