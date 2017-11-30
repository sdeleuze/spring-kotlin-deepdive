package io.spring.deepdive.repository

import io.spring.deepdive.model.ArticleEvent
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ArticleEventRepository : ReactiveCrudRepository<ArticleEvent, String> {

    @Tailable
    fun findWithTailableCursorBy(): Flux<ArticleEvent>

}