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

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class HtmlController(private val repository: ArticleRepository, private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun blog(model: Model): String {
        val articles = repository.findAllByOrderByAddedAtDesc().map { it.toDto(markdownConverter) }
        model.addAttribute("title", "Blog")
        model.addAttribute("articles", articles)
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun post(@PathVariable slug: String, model: Model): String {
        val article = repository.findOne(slug)
        Assert.notNull(article, "Wrong article slug provided")
        model.addAttribute("article", article.toDto(markdownConverter))
        return "article"
    }

}
