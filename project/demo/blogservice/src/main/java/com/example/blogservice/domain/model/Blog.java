package com.example.blogservice.domain.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "blogs", indexes = {
        @Index(name = "idx_blogs_category", columnList = "category"),
        @Index(name = "idx_blogs_createdAt", columnList = "createdAt")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Blog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String blogName;

    @Column(nullable = false, length = 200)
    private String category;

    @Lob
    @Column(nullable = false)
    private String article;

    @Column(nullable = false, length = 100)
    private String authorName;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Long ownerId;
}