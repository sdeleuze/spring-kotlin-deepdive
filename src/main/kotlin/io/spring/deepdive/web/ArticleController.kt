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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository, private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String, @RequestParam(required = false) converter: String?) = when (converter) {
        "markdown" -> repository.findOne(slug).let { it.copy(
                headline = markdownConverter.invoke(it.headline),
                content = markdownConverter.invoke(it.content)) }
        null -> repository.findOne(slug)
        else -> throw IllegalArgumentException("Only markdown converter is supported")
    }

}
