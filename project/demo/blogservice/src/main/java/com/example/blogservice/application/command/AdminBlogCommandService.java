package com.example.blogservice.application.command;

import com.example.blogservice.domain.model.Blog;
import com.example.blogservice.domain.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AdminBlogCommandService {
    private final BlogRepository blogRepository;

    public AdminBlogCommandService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Transactional
    public Long adminAddBlog(String blogName, String category, String article, String authorName, Long ownerId) {
        Blog blog = Blog.builder()
                .blogName(blogName)
                .category(category)
                .article(article)
                .authorName(authorName)
                .createdAt(Instant.now())
                .ownerId(ownerId)
                .build();
        return blogRepository.save(blog).getId();
    }
}