package com.ritier.springr2dbcsample.service

import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.flow.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("local")
class PostingServiceTest : FunSpec() {
//    private lateinit var postingCustomRepository: PostingCustomRepository
//    private lateinit var postingService: PostingService
//
//    init {
//        beforeTest {
//            postingCustomRepository = mockk<PostingCustomRepository>(relaxed = true)
//            postingService = PostingService(postingCustomRepository = postingCustomRepository)
//        }
//        afterTest {
//            clearMocks(postingCustomRepository)
//        }
//
//        test("should return 1 posting").config(coroutineTestScope = true) {
//
//            val postingEntity = Posting(
//                id = 1,
//                contents = "asdasda",
//                createdAt = LocalDateTime.MAX,
//                user = User(
//                    id = 1,
//                    nickname = "terry",
//                    age = 10,
//                    profileImg = Image(
//                        id = 1,
//                        url = "Asdasd",
//                        width = 1030,
//                        height = 1040,
//                        createdAt = LocalDateTime.MAX,
//                    ),
//                    profileImgId = 1,
//                ),
//                images = listOf(),
//                comments = listOf(),
//                userId = 1,
//            )
//
//            // mock repository result
//            coEvery {
//                postingCustomRepository.findAll()
//            } coAnswers {
//                flow {
//                    emit(postingEntity)
//                }
//            }
//
//            // compare
//            val res: Int = postingService.findAll().count()
//            val expectedRes: Int = flow {
//                emit(PostingDto.from(postingEntity))
//            }.count()
//
//            println(res)
//            println(expectedRes)
//
//            res shouldBe expectedRes
//        }
//    }
}