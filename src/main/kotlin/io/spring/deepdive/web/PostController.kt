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
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/post")
class PostController(private val postRepository: PostRepository,
                     private val postEventRepository: PostEventRepository,
                     private val markdownConverter: MarkdownConverter) {

    val notifications = postEventRepository.count().flatMapMany { postEventRepository.findWithTailableCursorBy().skip(it) }.share()

    @GetMapping("/")
    fun findAll() = postRepository.findAll()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String, @RequestParam converter: String?) = when (converter) {
        "markdown" -> postRepository.findById(slug).map { it.copy(
                title = markdownConverter.apply(it.title),
                headline = markdownConverter.apply(it.headline),
                content = markdownConverter.apply(it.content)) }
        null -> postRepository.findById(slug)
        else -> throw IllegalArgumentException("Only markdown converter is supported")
    }

    @PostMapping("/")
    fun save(@RequestBody post: Post) = postRepository.save(post)

    @DeleteMapping("/{slug}")
    fun delete(@PathVariable slug: String) = postRepository.deleteById(slug)

    @GetMapping("/notifications", produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    fun notifications() = notifications

}
