package com.example.blogservice.application.query;



        import com.example.blogservice.domain.dto.BlogSearchItem;
        import com.example.blogservice.domain.dto.BlogSearchResponse;
        import com.example.blogservice.domain.model.Blog;
        import com.example.blogservice.domain.repository.BlogRepository;
        import org.springframework.stereotype.Service;

        import java.time.Instant;
        import java.util.stream.Collectors;

@Service
public class BlogQueryService {
    private final BlogRepository blogRepository;

    public BlogQueryService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogSearchResponse searchByCategory(String category) {
        var items = blogRepository.findByCategory(category).stream()
                .map(this::toItem)
                .collect(Collectors.toList());
        return new BlogSearchResponse.Builder()
                .category(category)
                .addItems(items)
                .build();
    }

    public BlogSearchResponse searchByCategoryAndRange(String category, Instant from, Instant to) {
        var items = blogRepository.findByCategoryAndDateRange(category, from, to).stream()
                .map(this::toItem)
                .collect(Collectors.toList());
        return new BlogSearchResponse.Builder()
                .category(category)
                .range(from, to)
                .addItems(items)
                .build();
    }

    private BlogSearchItem toItem(Blog b) {
        return BlogSearchItem.builder()
                .id(b.getId())
                .blogName(b.getBlogName())
                .category(b.getCategory())
                .authorName(b.getAuthorName())
                .createdAt(b.getCreatedAt())
                .build();
    }
}