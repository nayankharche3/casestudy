package com.example.userservice.api.query;

        import com.example.userservice.application.query.UserQueryService;
        import com.example.userservice.domain.dto.BlogResponse;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1.0/blogsite/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<BlogResponse>> getAllOwnBlogs(@RequestHeader("X-USER-ID") Long ownerId) {
        return ResponseEntity.ok(userQueryService.getAllOwnBlogs(ownerId));
    }
}