package com.example.blogservice.api.command;

import javax.validation.constraints.Size;

public class BlogRequest {

    @Size(min = 20, max = 200)
    private String category;

    @Size(min = 1000)
    private String article;

    @Size(min = 3, max = 100)
    private String authorName;

    // Getters and setters
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getArticle() {
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }

    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
