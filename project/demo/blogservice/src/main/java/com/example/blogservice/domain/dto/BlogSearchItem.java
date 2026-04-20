package com.example.blogservice.domain.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class BlogSearchItem {
    private Long id;
    private String blogName;
    private String category;
    private String authorName;
    private Instant createdAt;
}