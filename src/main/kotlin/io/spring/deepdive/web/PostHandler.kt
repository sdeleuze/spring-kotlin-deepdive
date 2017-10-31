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
import io.spring.deepdive.model.Post
import io.spring.deepdive.repository.PostEventRepository
import io.spring.deepdive.repository.PostRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.toMono

@Component
class PostHandler(private val postRepository: PostRepository,
                  private val postEventRepository: PostEventRepository,
                  private val markdownConverter: MarkdownConverter) {

    val notifications = postEventRepository.count().flatMapMany { postEventRepository.findWithTailableCursorBy().skip(it) }.share()

    fun findAll(req: ServerRequest) =
            ok().body(postRepository.findAll())

    fun findOne(req: ServerRequest) =
            ok().body(req.queryParam("converter")
                    .map {
                        if (it == "markdown")
                            postRepository.findById(req.pathVariable("slug")).map {
                                it.copy(
                                        headline = markdownConverter.invoke(it.headline),
                                        content = markdownConverter.invoke(it.content))
                            }
                        else IllegalArgumentException("Only markdown converter is supported").toMono() }
                    .orElse(postRepository.findById(req.pathVariable("slug"))))

    fun save(req: ServerRequest) =
            ok().body(postRepository.saveAll(req.bodyToMono<Post>()))

    fun delete(req: ServerRequest) =
            ok().body(postRepository.deleteById(req.pathVariable("slug")))

    fun notifications(req: ServerRequest) =
            ok().bodyToServerSentEvents(notifications)

}
