package com.example.userservice.domain.dto;

        import lombok.*;
        import java.time.Instant;

@Getter @Setter @Builder@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {
    private Long id;
    private String blogName;
    private String category;
    private String article;
    private String authorName;
    private Instant createdAt;
}