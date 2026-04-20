package com.example.userservice.application.query;


import com.example.userservice.domain.dto.BlogResponse;
import com.example.userservice.domain.model.User;
import com.example.userservice.domain.model.Blog;
import com.example.userservice.domain.repository.UserRepository;
import com.example.userservice.domain.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    void getAllOwnBlogs_shouldReturnBlogResponses() {
        User owner = User.builder().id(1L).username("owner").build();
        Blog blog = Blog.builder()
                .id(10L)
                .blogName("BlogName")
                .category("Tech")
                .article("Article")
                .authorName("Author")
                .createdAt(Instant.now())
                .owner(owner)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(blogRepository.findAllByOwner(owner)).thenReturn(List.of(blog));

        List<BlogResponse> responses = userQueryService.getAllOwnBlogs(1L);

        assertEquals(1, responses.size());
        assertEquals("BlogName", responses.get(0).getBlogName());
        assertEquals("Tech", responses.get(0).getCategory());
    }
}