/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.spring.deepdive.web

import io.spring.deepdive.MarkdownConverter
import io.spring.deepdive.model.Article
import io.spring.deepdive.repository.ArticleEventRepository
import io.spring.deepdive.repository.ArticleRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.toMono

@Component
class ArticleHandler(private val articleRepository: ArticleRepository,
                     private val articleEventRepository: ArticleEventRepository,
                     private val markdownConverter: MarkdownConverter) {

    val notifications = articleEventRepository
            .count()
            .flatMapMany { articleEventRepository.findWithTailableCursorBy().skip(it) }
            .share()

    fun findAll(req: ServerRequest) =
            ok().body(articleRepository.findAll())

    fun findOne(req: ServerRequest) =
            ok().body(req.queryParam("converter")
                    .map {
                        if (it == "markdown")
                            articleRepository.findById(req.pathVariable("slug")).map {
                                it.copy(
                                        headline = markdownConverter.invoke(it.headline),
                                        content = markdownConverter.invoke(it.content))
                            }
                        else IllegalArgumentException("Only markdown converter is supported").toMono() }
                    .orElse(articleRepository.findById(req.pathVariable("slug"))))

    fun save(req: ServerRequest) =
            ok().body(articleRepository.saveAll(req.bodyToMono<Article>()))

    fun delete(req: ServerRequest) =
            ok().body(articleRepository.deleteById(req.pathVariable("slug")))

    fun notifications(req: ServerRequest) =
            ok().bodyToServerSentEvents(notifications)

}
