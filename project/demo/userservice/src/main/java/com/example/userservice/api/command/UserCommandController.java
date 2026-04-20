package com.example.userservice.api.command;

        import com.example.userservice.application.command.UserCommandService;
        import com.example.userservice.domain.dto.*;
        import org.springframework.http.ResponseEntity;
        import org.springframework.validation.annotation.Validated;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/blogsite/user")
@Validated
public class UserCommandController {

    private final UserCommandService userCommandService;

    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody RegisterUserRequest request) {
        Long id = userCommandService.register(request);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/blogs/add/{blogname}")
    public ResponseEntity<Long> addBlog(@PathVariable("blogname") String blogName,
                                        @Valid @RequestBody BlogCreateRequest request,
                                        @RequestHeader("X-USER-ID") Long ownerId) {
        request.setBlogName(blogName); // enforce consistency
        Long id = userCommandService.addBlog(ownerId, request);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete/{blogname}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("blogname") String blogName,
                                           @RequestHeader("X-USER-ID") Long ownerId) {
        userCommandService.deleteOwnBlog(ownerId, blogName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/blogs/update/{id}")
    public ResponseEntity<Void> updateBlog(@PathVariable("id") Long id,
                                           @Valid @RequestBody BlogUpdateRequest request,
                                           @RequestHeader("X-USER-ID") Long ownerId) {
        userCommandService.updateOwnBlog(ownerId, id, request);
        return ResponseEntity.noContent().build();
    }
}