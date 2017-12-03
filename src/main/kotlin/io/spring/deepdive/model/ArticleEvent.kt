package io.spring.deepdive.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ArticleEvent(@Id val slug: String, val title: String)