package com.example.blogservice.application.query;

import com.example.blogservice.domain.dto.BlogSearchResponse;
import com.example.blogservice.domain.model.Blog;
import com.example.blogservice.domain.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogQueryServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogQueryService blogQueryService;

    @Test
    void searchByCategory_shouldReturnResponseWithItems() {
        Blog blog = Blog.builder()
                .id(1L)
                .blogName("Tech Blog")
                .category("Tech")
                .authorName("Author")
                .createdAt(Instant.now())
                .build();

        when(blogRepository.findByCategory("Tech")).thenReturn(List.of(blog));

        BlogSearchResponse response = blogQueryService.searchByCategory("Tech");

        assertEquals("Tech", response.getCategory());
        assertEquals(1, response.getItems().size());
        assertEquals("Tech Blog", response.getItems().get(0).getBlogName());
    }

    @Test
    void searchByCategoryAndRange_shouldReturnResponseWithItems() {
        Instant from = Instant.now().minusSeconds(3600);
        Instant to = Instant.now();

        Blog blog = Blog.builder()
                .id(2L)
                .blogName("Range Blog")
                .category("Tech")
                .authorName("Author")
                .createdAt(Instant.now())
                .build();

        when(blogRepository.findByCategoryAndDateRange("Tech", from, to))
                .thenReturn(List.of(blog));

        BlogSearchResponse response = blogQueryService.searchByCategoryAndRange("Tech", from, to);

        assertEquals("Tech", response.getCategory());
        assertEquals(1, response.getItems().size());
        assertEquals("Range Blog", response.getItems().get(0).getBlogName());
    }
}