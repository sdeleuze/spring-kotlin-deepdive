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
import io.spring.deepdive.Utils;
import io.spring.deepdive.model.Article;
import io.spring.deepdive.model.User;

class PostDto {

    private final String slug;

    private final String title;

    private final String headline;

    private final String content;

    private final User author;

    private final String addedAt;


    public PostDto(Article article, MarkdownConverter markdownConverter) {
        this.slug = article.getSlug();
        this.title = article.getTitle();
        this.headline = markdownConverter.apply(article.getHeadline());
        this.content = markdownConverter.apply(article.getContent());
        this.author = article.getAuthor();
        this.addedAt = Utils.formatToEnglish(article.getAddedAt());
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public String getHeadline() {
        return headline;
    }

}
