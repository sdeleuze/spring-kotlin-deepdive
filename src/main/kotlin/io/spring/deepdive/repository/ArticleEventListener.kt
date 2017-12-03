package io.spring.deepdive.repository

import io.spring.deepdive.model.Article
import io.spring.deepdive.model.ArticleEvent
import kotlinx.coroutines.experimental.launch
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent
import org.springframework.stereotype.Component

@Component
class ArticleEventListener(private val articleEventRepository: ArticleEventRepository) : AbstractMongoEventListener<Article>() {

    override fun onAfterSave(event: AfterSaveEvent<Article>) {
        launch {
            articleEventRepository.save(ArticleEvent(event.source.slug, event.source.title))
        }
    }

}