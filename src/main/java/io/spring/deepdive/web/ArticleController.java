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
package io.spring.deepdive.web;

import io.spring.deepdive.MarkdownConverter;
import io.spring.deepdive.model.Article;
import io.spring.deepdive.repository.ArticleRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    private final MarkdownConverter markdownConverter;

    public ArticleController(ArticleRepository articleRepository, MarkdownConverter markdownConverter) {
        this.articleRepository = articleRepository;
        this.markdownConverter = markdownConverter;
    }

    @GetMapping("/")
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @GetMapping("/{slug}")
    public Article findOne(@PathVariable String slug, @RequestParam(required = false) String converter) {
        Article article = articleRepository.findOne(slug);
        if (converter != null) {
            if (converter.equals("markdown")) {
                article.setHeadline(markdownConverter.apply(article.getHeadline()));
                article.setContent(markdownConverter.apply(article.getContent()));

            }
            else {
                throw new IllegalArgumentException("Only markdown converter is supported");
            }
        }
        return article;
    }

}
