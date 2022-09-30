package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Posting
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PostingRepository : ReactiveCrudRepository<Posting, Long> {
}

// blocking issue 떄문에 join 후 aggregate 는 보류
//@Repository
//class PostingCustomRepository {
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
//                    "LEFT JOIN images AS i ON " +
//                    "INNER JOIN posting_images AS pi ON pi.posting_id = p.posting_id" +
//                    "INNER JOIN images AS i ON i.image_id = pi.image_id" +
//                    "INNER JOIN posting_comments AS c ON c.posting_id = p.posting_id"
//        ).fetch().all().bufferUntilChanged<String> { it["posting_id"].toString() }.map { mapping(it) }.asFlow()
//
//    fun mapping(list: List<Map<String, *>>): Posting {
//        val postingMap = list[0]
//        val posting = Posting(
//            id = postingMap["posting_id"].toString().toLong(),
//            contents = postingMap["posting_contents"].toString(),
//            createdAt = Converter.convertStrToLocalDateTime(postingMap["posting_created_at"].toString()),
//            userId = postingMap["user_id"].toString().toLong(),
//            user = User(
//                id = postingMap["user_id"].toString().toLong(),
//                nickname = postingMap["user_nickname"].toString(),
//                age = postingMap["user_age"].toString().toInt(),
//                profileImgId = if(postingMap["user_profile_img_id"] == null) null else postingMap["user_profile_img_id"].toString().toLong(),
//                profileImg = null
//            ),
//            comments = listOf(),
//            images = listOf(),
//        )
//
//        val comments = list.stream().map {
//            Comment(
//                id = it["comment_id"].toString().toLong(),
//                contents = it["comment_contents"].toString(),
//                createdAt = Converter.convertStrToLocalDateTime(it["comment_created_at"].toString()),
//                userId =
//            )
//        }
//        return
//    }
//}