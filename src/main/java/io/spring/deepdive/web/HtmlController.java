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

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.spring.deepdive.MarkdownConverter;
import io.spring.deepdive.model.Post;
import io.spring.deepdive.repository.PostRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HtmlController {

    private final PostRepository postRepository;

    private final MarkdownConverter markdownConverter;


    public HtmlController(PostRepository postRepository, MarkdownConverter markdownConverter) {
        this.postRepository = postRepository;
        this.markdownConverter = markdownConverter;
    }

    @GetMapping(value = "/")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        Iterable<PostDto> postDtos = StreamSupport.stream(posts.spliterator(), false).map(post -> new PostDto(post, markdownConverter)).collect(Collectors.toList());
        model.addAttribute("title", "Blog");
        model.addAttribute("posts", postDtos);
        return "blog";
    }

    @GetMapping("/{slug}")
    public String post(@PathVariable String slug, Model model) {
        Post post = postRepository.findOne(slug);
        Assert.notNull(post, "Wrong post slug provided");
        PostDto postDto = new PostDto(post, markdownConverter);
        model.addAttribute("post", postDto);
        return "post";
    }

}
