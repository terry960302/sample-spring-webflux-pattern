package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.dto.CommentDto
import com.ritier.springr2dbcsample.entity.Comment
import com.ritier.springr2dbcsample.repository.CommentRepository
import com.ritier.springr2dbcsample.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService {
    @Autowired
    private lateinit var commentRepository: CommentRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun findCommentsByPostingId(postingId: Long): Flow<CommentDto> =
        commentRepository.findCommentsByPostingId(postingId).map { loadOneToOneRelation(it) }

    suspend fun loadOneToOneRelation(comment  : Comment) : CommentDto = withContext(Dispatchers.IO){
        val deferredUser = async { userRepository.findById(comment.userId) }
        comment.user = deferredUser.await()
        CommentDto.from(comment)
    }
}