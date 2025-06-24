package com.ritier.springr2dbcsample.application.service

import com.ritier.springr2dbcsample.common.exception.AppException
import com.ritier.springr2dbcsample.common.exception.ErrorCode
import com.ritier.springr2dbcsample.domain.repository.PostingRepository
import com.ritier.springr2dbcsample.domain.vo.posting.PostingId
import com.ritier.springr2dbcsample.domain.vo.user.UserId
import com.ritier.springr2dbcsample.presentation.dto.posting.PostingDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostingService(private val postingRepository: PostingRepository) {

    private val logger = KotlinLogging.logger {}

    suspend fun findPostingById(id: Long): PostingDto {
        val posting = postingRepository.findById(PostingId(id))
            ?: throw AppException(ErrorCode.POSTING_NOT_FOUND)

        logger.info("게시물 조회 성공 - $id")
        return PostingDto.from(posting)
    }

    // 성능 최적화: bufferUntilChanged 패턴으로 N+1 해결
    suspend fun findAllPostings(): Flow<PostingDto> {
        return postingRepository.findAll()
            .map { PostingDto.from(it) } // 연관관계 데이터 포함
    }

    suspend fun findPostingsByUserId(userId: Long): Flow<PostingDto> {
        return postingRepository.findByUserId(UserId(userId))
            .map { PostingDto.from(it) }
    }


    // FIXME: > suspend fun findAll(): Flow<PostingDto> = postingRepository.findAll().asFlow().map(this::loadRelations)
    // Took average 300ms~500ms to take response from client.(too slow)
    // !!! non-blocking 과 관리용이성을 보장하면서 성능을 개선하는 방법을 보완할 필요 !!!
    //    @Autowired
    //    private lateinit var postingRepository: PostingRepository
    //    @Autowired
    //    private lateinit var postingImageRepository: PostingImageRepository
    //    @Autowired
    //    private lateinit var userRepository: UserRepository
    //    suspend fun loadRelations(posting: Posting): PostingDto = withContext(Dispatchers.IO) {
    //        val deferredUser = async { userRepository.findById(posting.userId) }
    //        val deferredImages = async { postingImageRepository.findPostingImagesByPostingId(posting.id) }
    //        posting.user = deferredUser.await()
    //        posting.images = deferredImages.await().map { it.image!! }.toList()
    //        PostingDto.from(posting)
    //    }
}