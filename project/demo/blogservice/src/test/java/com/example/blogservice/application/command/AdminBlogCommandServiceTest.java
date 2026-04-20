package com.example.blogservice.application.command;

import com.example.blogservice.domain.model.Blog;
import com.example.blogservice.domain.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminBlogCommandServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private AdminBlogCommandService adminBlogCommandService;

    @Test
    void adminAddBlog_shouldSaveBlogAndReturnId() {
        Blog blog = Blog.builder()
                .id(1L)
                .blogName("Test Blog")
                .category("Tech")
                .article("Content")
                .authorName("Author")
                .ownerId(99L)
                .createdAt(Instant.now())
                .build();

        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        Long id = adminBlogCommandService.adminAddBlog(
                "Test Blog", "Tech", "Content", "Author", 99L);

        assertEquals(1L, id);
        verify(blogRepository, times(1)).save(any(Blog.class));
    }
}