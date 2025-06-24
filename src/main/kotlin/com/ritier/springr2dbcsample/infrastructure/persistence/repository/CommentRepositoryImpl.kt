package com.ritier.springr2dbcsample.infrastructure.persistence.repository

import com.ritier.springr2dbcsample.domain.model.Comment
import com.ritier.springr2dbcsample.domain.repository.CommentRepository
import com.ritier.springr2dbcsample.domain.vo.posting.CommentId
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl(
    private val commentCrudRepository: CommentCrudRepository
) : CommentRepository {

    override suspend fun findByPostingId(postingId: PostingId): Flow<Comment> {
        return commentCrudRepository.findByPostingId(postingId.value)
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