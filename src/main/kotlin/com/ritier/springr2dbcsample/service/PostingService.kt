package com.ritier.springr2dbcsample.service

import com.ritier.springr2dbcsample.dto.PostingDto
import com.ritier.springr2dbcsample.entity.Posting
import com.ritier.springr2dbcsample.repository.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostingService {


    @Autowired
    private lateinit var postingCustomRepository: PostingCustomRepository


    suspend fun findById(id: Long): PostingDto? =
        if (postingCustomRepository.findById(id) == null) null else PostingDto.from(postingCustomRepository.findById(id)!!)

    suspend fun findAll(): Flow<PostingDto> =
        postingCustomRepository.findAll().map { PostingDto.from(it) }

    // [NEED FIX] > suspend fun findAll(): Flow<PostingDto> = postingRepository.findAll().asFlow().map(this::loadRelations)
    // Took average 300ms~500ms to take response from client.(too slow)
    // TODO: non-blocking 과 관리용이성을 보장하면서 성능을 개선하는 방법을 보완할 필요
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