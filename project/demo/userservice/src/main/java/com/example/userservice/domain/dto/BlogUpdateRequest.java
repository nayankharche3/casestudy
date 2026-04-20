package com.example.userservice.domain.dto;

        import lombok.*;
        import javax.validation.constraints.*;

@Getter @Setter@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateRequest {
    @NotBlank @Size(min = 20, max = 200)
    private String blogName;

    @NotBlank @Size(min = 20, max = 200)
    private String category;

    @NotBlank @Size(min = 1000)
    private String article;

    @NotBlank @Size(min = 3, max = 100)
    private String authorName;
}