package com.ritier.springr2dbcsample.repository

import com.ritier.springr2dbcsample.entity.Image
import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.entity.User
import com.ritier.springr2dbcsample.util.ConverterUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.reactive.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@Repository
interface PostingRepository : ReactiveCrudRepository<Posting, Long> {
}

// blocking issue 떄문에 join 후 aggregate 는 보류
@Repository
class PostingCustomRepository(val databaseClient: DatabaseClient) {

    // Fetch user with profile img data(nullable)
    private val fetchUserQuery = "SELECT " +
            "u.user_id as user_id," +
            "u.nickname as user_nickname," +
            "u.age as user_age," +
            "u.profile_img_id as user_profile_img_id," +
            "i.url as user_profile_img_url," +
            "i.width as user_profile_img_width," +
            "i.height as user_profile_img_height," +
            "i.created_at as user_profile_img_created_at " +
            "FROM users AS u " +
            "LEFT JOIN images AS i ON i.image_id = u.profile_img_id"

    // Fetch All postings with posting_user(with profileImg metadata) and posting_images(list)
    private val fetchAllPostingsQuery = "SELECT " +
            "u.*," +
            "p.posting_id," +
            "p.contents AS posting_contents," +
            "p.created_at AS posting_created_at," +
            "i.image_id AS posting_img_id," +
            "i.url AS posting_img_url," +
            "i.width AS posting_img_width," +
            "i.height AS posting_img_height," +
            "i.created_at AS posting_img_created_at " +
            "FROM postings AS p " +
            "INNER JOIN ($fetchUserQuery) AS u ON u.user_id = p.user_id " +
            "INNER JOIN posting_images AS pi ON pi.posting_id = p.posting_id " +
            "INNER JOIN images AS i ON pi.image_id = i.image_id"

    // Do count query before fetch single posting data for handling Null error process like most ORMs did
    private val countPostingQuery =
        "SELECT COUNT(*) FROM postings WHERE posting_id = :postingId"

    // Fetch Single posting by using `Sub query` instead of using `WHERE` clause at end of postingsQuery variable for performance
    private val fetchPostingQuery =
        "SELECT\n" +
                "u.*,\n" +
                "p.posting_id,\n" +
                "p.contents AS posting_contents,\n" +
                "p.created_at AS posting_created_at,\n" +
                "i.image_id AS posting_img_id,\n" +
                "i.url AS posting_img_url,\n" +
                "i.width AS posting_img_width,\n" +
                "i.height AS posting_img_height,\n" +
                "i.created_at AS posting_img_created_at\n" +
                "FROM (SELECT * FROM postings WHERE posting_id = :postingId) AS p \n" +
                "INNER JOIN ($fetchUserQuery) AS u ON u.user_id = p.user_id \n" +
                "INNER JOIN posting_images AS pi ON pi.posting_id = p.posting_id \n" +
                "INNER JOIN images AS i ON pi.image_id = i.image_id"

    suspend fun findById(postingId: Long): Posting? {

        val count = databaseClient.sql(countPostingQuery).bind("postingId", postingId).fetch().one()
            .awaitLast()["count"].toString().toInt()
        if (count <= 0) return null

        return databaseClient.sql(fetchPostingQuery).bind("postingId", postingId).fetch().all()
            .bufferUntilChanged<String> { it["posting_id"].toString() }
            .map { convertRowListToPostingEntity(it) }.take(1).awaitLast()
    }

    suspend fun findAll(): Flow<Posting> =
        databaseClient.sql(fetchAllPostingsQuery).fetch().all().processPostingsRawData()

    fun Flux<Map<String, *>>.processPostingsRawData(): Flow<Posting> {
        if (this.count().equals(0)) return Flux.empty<Posting>().asFlow()
        return this.bufferUntilChanged<String> { it["posting_id"].toString() }.map { convertRowListToPostingEntity(it) }
            .asFlow()
    }

    fun convertRowListToPostingEntity(list: List<Map<String, *>>): Posting {
        val postingMap = list[0]
        val posting = Posting(
            id = postingMap["posting_id"].toString().toLong(),
            contents = postingMap["posting_contents"].toString(),
            createdAt = ConverterUtil.convertStrToLocalDateTime(postingMap["posting_created_at"].toString()),
            userId = postingMap["user_id"].toString().toLong(),
            user = User(
                id = postingMap["user_id"].toString().toLong(),
                nickname = postingMap["user_nickname"].toString(),
                age = postingMap["user_age"].toString().toInt(),
                profileImgId = if (postingMap["user_profile_img_id"] == null) null else postingMap["user_profile_img_id"].toString()
                    .toLong(),
                profileImg = if (postingMap["user_profile_img_id"] == null) null else Image(
                    id = postingMap["user_profile_img_id"].toString().toLong(),
                    url = postingMap["user_profile_img_url"].toString(),
                    width = postingMap["user_profile_img_width"].toString().toInt(),
                    height = postingMap["user_profile_img_height"].toString().toInt(),
                    createdAt = ConverterUtil.convertStrToLocalDateTime(postingMap["user_profile_img_created_at"].toString()),
                )
            ),
            comments = null,
            images = null,
        )
        val postingImages = list.stream().map {
            Image(
                id = postingMap["posting_img_id"].toString().toLong(),
                url = postingMap["posting_img_url"].toString(),
                width = postingMap["posting_img_width"].toString().toInt(),
                height = postingMap["posting_img_height"].toString().toInt(),
                createdAt = ConverterUtil.convertStrToLocalDateTime(postingMap["posting_img_created_at"].toString()),
            )
        }.collect(Collectors.toList())

        posting.images = postingImages
        return posting
    }
}