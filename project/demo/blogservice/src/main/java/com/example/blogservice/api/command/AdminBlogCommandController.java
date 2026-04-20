package com.example.blogservice.api.command;

import com.example.blogservice.application.command.AdminBlogCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1.0/blogsite/user") // ✅ mapped under /user
@Validated
public class AdminBlogCommandController {

    private final AdminBlogCommandService adminService;

    public AdminBlogCommandController(AdminBlogCommandService adminService) {
        this.adminService = adminService;
    }

    // USER or ADMIN can add blogs
    // POST /api/v1.0/blogsite/user/blogs/add/{blogname}
    @PostMapping("/blogs/add/{blogname}")
    public ResponseEntity<Long> addBlog(@PathVariable("blogname") @Size(min = 20, max = 200) String blogname,
                                        @Valid @RequestBody BlogRequest blogRequest,
                                        @RequestHeader("X-OWNER-ID") Long ownerId) {
        Long id = adminService.adminAddBlog(blogname,
                blogRequest.getCategory(),
                blogRequest.getArticle(),
                blogRequest.getAuthorName(),
                ownerId);
        System.out.println(">>> Controller invoked with ownerId=" + ownerId);

        return ResponseEntity.ok(id);
    }
}
