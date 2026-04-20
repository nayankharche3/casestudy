package com.example.userservice.application.command;

import com.example.userservice.domain.dto.RegisterUserRequest;
import com.example.userservice.domain.dto.BlogCreateRequest;
import com.example.userservice.domain.dto.BlogUpdateRequest;
import com.example.userservice.domain.model.User;
import com.example.userservice.domain.model.Blog;
import com.example.userservice.domain.repository.UserRepository;
import com.example.userservice.domain.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    void register_shouldSaveUserAndReturnId() {
        RegisterUserRequest request = new RegisterUserRequest("newuser", "new@example.com", "password123");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = User.builder().id(1L).username("newuser").email("new@example.com").passwordHash("encodedPassword").role("USER").build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Long id = userCommandService.register(request);

        assertEquals(1L, id);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void addBlog_shouldSaveBlogAndReturnId() {
        User owner = User.builder().id(1L).username("owner").build();
        BlogCreateRequest request = new BlogCreateRequest("BlogName", "Tech", "Article", "Author");

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        Blog savedBlog = Blog.builder().id(10L).blogName("BlogName").owner(owner).createdAt(Instant.now()).build();
        when(blogRepository.save(any(Blog.class))).thenReturn(savedBlog);

        Long id = userCommandService.addBlog(1L, request);

        assertEquals(10L, id);
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    void deleteOwnBlog_shouldDeleteBlogIfOwned() {
        User owner = User.builder().id(1L).username("owner").build();
        Blog blog = Blog.builder().id(10L).blogName("BlogName").owner(owner).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(blogRepository.findByOwnerAndBlogName(owner, "BlogName")).thenReturn(Optional.of(blog));

        userCommandService.deleteOwnBlog(1L, "BlogName");

        verify(blogRepository).delete(blog);
    }

    @Test
    void updateOwnBlog_shouldUpdateBlogIfOwned() {
        User owner = User.builder().id(1L).username("owner").build();
        Blog blog = Blog.builder().id(10L).blogName("OldName").owner(owner).build();

        BlogUpdateRequest request = new BlogUpdateRequest("NewName", "Tech", "Updated Article", "Author");

        when(blogRepository.findById(10L)).thenReturn(Optional.of(blog));

        userCommandService.updateOwnBlog(1L, 10L, request);

        assertEquals("NewName", blog.getBlogName());
        assertEquals("Updated Article", blog.getArticle());
        verify(blogRepository).save(blog);
    }
}