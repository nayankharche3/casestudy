package com.example.userservice.domain.dto;

import lombok.*;
import javax.validation.constraints.*;

@Getter @Setter@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Pattern(
            regexp = "^[^\\s@]+@[^\\s@]+\\.com$",
            message = "Email must contain @ and end with .com"
    )
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
            message = "Password must be minimum 8 characters, include letters, digits, and special characters"
    )
    private String password;
}