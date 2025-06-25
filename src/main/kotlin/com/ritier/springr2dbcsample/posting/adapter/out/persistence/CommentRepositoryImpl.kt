package com.ritier.springr2dbcsample.posting.adapter.out.persistence

import com.ritier.springr2dbcsample.posting.domain.model.Comment
import com.ritier.springr2dbcsample.posting.domain.port.output.CommentRepository
import com.ritier.springr2dbcsample.posting.domain.vo.CommentId
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl(
    private val crudRepository: CommentCrudRepository
) : CommentRepository {
    override suspend fun save(comment: Comment): Comment {
        val saved =crudRepository.save(comment.toEntity()).awaitSingle()
        return Comment.fromEntity(saved);
    }

    override suspend fun findByPostingId(postingId: PostingId): Flow<Comment> {
        return crudRepository.findByPostingId(postingId.value)
            .map { entity ->
                Comment(
                    id = CommentId(entity.id),
                    userId = UserId(entity.userId),
                    postingId = PostingId(entity.postingId),
                    contents = entity.contents,
                    createdAt = entity.createdAt
                    // user = null (JOIN 안했으므로)
                )
            }
    }
}