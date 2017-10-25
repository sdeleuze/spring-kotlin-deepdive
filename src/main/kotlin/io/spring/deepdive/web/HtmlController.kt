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
import io.spring.deepdive.repository.PostRepository
import io.spring.deepdive.repository.UserRepository

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.reactive.result.view.Rendering

import reactor.core.publisher.toMono

@Controller
class HtmlController(private val userRepository: UserRepository, private val postRepository: PostRepository, private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun blog() = Rendering.view("blog")
            .model(mapOf(
                    "title" to "Blog",
                    "posts" to postRepository.findAll().flatMap { it.toDto(userRepository, markdownConverter) } ))
            .build()


    @GetMapping("/{slug}")
    fun post(@PathVariable slug: String, model: Model) = Rendering.view("post")
            .modelAttribute("post", postRepository
                    .findById(slug)
                    .flatMap { it.toDto(userRepository, markdownConverter) }
                    .switchIfEmpty(IllegalArgumentException("Wrong post slug provided").toMono()))
            .build()

}
