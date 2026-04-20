package com.example.userservice.domain.model;

        import lombok.*;
        import javax.persistence.*;
        import java.time.Instant;

@Entity
@Table(name = "blogs")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}