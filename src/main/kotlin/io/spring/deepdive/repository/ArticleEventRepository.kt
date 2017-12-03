package io.spring.deepdive.repository

import io.spring.deepdive.model.ArticleEvent
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import org.springframework.data.mongodb.repository.CoroutineMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.stereotype.Repository

@Repository
interface ArticleEventRepository : CoroutineMongoRepository<ArticleEvent, String> {

    @Tailable
    suspend fun findWithTailableCursorBy(): ReceiveChannel<ArticleEvent>

}
