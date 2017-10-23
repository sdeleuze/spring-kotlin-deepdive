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
import io.spring.deepdive.model.Post;
import io.spring.deepdive.model.User;


class PostDto {

    private final String slug;

    private final String title;

    private final String addedAt;

    private final String headline;

    private final String content;

    private final User author;


    public PostDto(Post post, MarkdownConverter markdownConverter) {
        this.slug = post.getSlug();
        this.title = post.getTitle();
        this.addedAt = Utils.formatToEnglish(post.getAddedAt());
        this.headline = markdownConverter.apply(post.getHeadline());
        this.content = markdownConverter.apply(post.getContent());
        this.author = post.getAuthor();
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public String getHeadline() {
        return headline;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

}
