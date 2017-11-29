package io.spring.deepdive.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Article {

    @Id
    private String slug;

    private String title;

    private String headline;

    private String content;

    @DBRef
    private User author;

    private LocalDateTime addedAt;


    public Article() {
    }

    public Article(String slug, String title, String headline, String content, User author) {
        this(slug, title, headline, content, author, LocalDateTime.now());
    }

    public Article(String slug, String title, String headline, String content, User author, LocalDateTime addedAt) {
        this.slug = slug;
        this.title = title;
        this.headline = headline;
        this.content = content;
        this.author = author;
        this.addedAt = addedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getHeadline() {
        return headline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (slug != null ? !slug.equals(article.slug) : article.slug != null) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (headline != null ? !headline.equals(article.headline) : article.headline != null)
            return false;
        if (content != null ? !content.equals(article.content) : article.content != null)
            return false;
        if (author != null ? !author.equals(article.author) : article.author != null)
            return false;
        return addedAt != null ? addedAt.equals(article.addedAt) : article.addedAt == null;
    }

    @Override
    public int hashCode() {
        int result = slug != null ? slug.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (headline != null ? headline.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (addedAt != null ? addedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", headline='" + headline + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", addedAt=" + addedAt +
                '}';
    }
}
