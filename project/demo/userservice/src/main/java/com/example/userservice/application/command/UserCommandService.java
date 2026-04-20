package com.example.userservice.application.command;

        import com.example.userservice.domain.dto.*;
        import com.example.userservice.domain.model.*;
        import com.example.userservice.domain.repository.*;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import java.time.Instant;

@Service
public class UserCommandService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCommandService(UserRepository userRepository,
                              BlogRepository blogRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long register(RegisterUserRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(u -> { throw new IllegalArgumentException("Username already exists"); });
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> { throw new IllegalArgumentException("Email already exists"); });

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        return userRepository.save(user).getId();
    }

    @Transactional
    public Long addBlog(Long ownerId, BlogCreateRequest request) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        Blog blog = Blog.builder()
                .blogName(request.getBlogName())
                .category(request.getCategory())
                .article(request.getArticle())
                .authorName(request.getAuthorName())
                .createdAt(Instant.now())
                .owner(owner)
                .build();
        return blogRepository.save(blog).getId();
    }

    @Transactional
    public void deleteOwnBlog(Long ownerId, String blogName) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        Blog blog = blogRepository.findByOwnerAndBlogName(owner, blogName)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found or not owned by user"));
        blogRepository.delete(blog);
    }

    @Transactional
    public void updateOwnBlog(Long ownerId, Long id, BlogUpdateRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        if (!blog.getOwner().getId().equals(ownerId)) {
            throw new IllegalStateException("Not allowed to update others' blogs");
        }
        blog.setBlogName(request.getBlogName());
        blog.setCategory(request.getCategory());
        blog.setArticle(request.getArticle());
        blog.setAuthorName(request.getAuthorName());
        blogRepository.save(blog);
    }
}