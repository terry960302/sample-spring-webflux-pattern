package com.ritier.springr2dbcsample.posting.adapter.out.persistence

import com.ritier.springr2dbcsample.posting.adapter.out.persistence.entity.PostingEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PostingCrudRepository : ReactiveCrudRepository<PostingEntity, Long> {
}