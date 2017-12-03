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
import io.spring.deepdive.repository.ArticleRepository
import io.spring.deepdive.repository.UserRepository
import org.springframework.http.HttpHeaders.*
import org.springframework.stereotype.Component

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*

import reactor.core.publisher.toMono

@Component
class HtmlHandler(private val userRepository: UserRepository,
                  private val articleRepository: ArticleRepository,
                  private val markdownConverter: MarkdownConverter) {

    fun blog(req: ServerRequest) = ok()
            .header(CONTENT_TYPE, "text/html;charset=UTF-8") // To be removed when SPR-16247 will be fixed
            .render("blog", mapOf(
                    "title" to "Blog",
                    "articles" to articleRepository.findAllByOrderByAddedAtDesc().concatMap { it.toDto(userRepository, markdownConverter) }
            ))

    fun article(req: ServerRequest) = ok()
            .header(CONTENT_TYPE, "text/html;charset=UTF-8") // To be removed when SPR-16247 will be fixed
            .render("article", mapOf("article" to articleRepository.findById(req.pathVariable("slug")).flatMap { it.toDto(userRepository, markdownConverter) }.switchIfEmpty(IllegalArgumentException("Wrong article slug provided").toMono())))

}
