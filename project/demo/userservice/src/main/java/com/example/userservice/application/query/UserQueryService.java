package com.example.userservice.application.query;

        import com.example.userservice.domain.dto.BlogResponse;
        import com.example.userservice.domain.model.User;
        import com.example.userservice.domain.repository.UserRepository;
        import com.example.userservice.domain.repository.BlogRepository;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.stream.Collectors;

@Service
public class UserQueryService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    public UserQueryService(UserRepository userRepository, BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    public List<BlogResponse> getAllOwnBlogs(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        return blogRepository.findAllByOwner(owner).stream()
                .map(b -> BlogResponse.builder()
                        .id(b.getId())
                        .blogName(b.getBlogName())
                        .category(b.getCategory())
                        .article(b.getArticle())
                        .authorName(b.getAuthorName())
                        .createdAt(b.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}