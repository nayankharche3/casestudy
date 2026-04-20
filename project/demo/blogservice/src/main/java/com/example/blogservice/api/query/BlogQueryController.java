package com.example.blogservice.api.query;


        import com.example.blogservice.application.query.BlogQueryService;
        import com.example.blogservice.domain.dto.BlogSearchResponse;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import java.time.LocalDate;
        import java.time.ZoneId;
        import java.time.Instant;

        import org.springframework.format.annotation.DateTimeFormat;


        import java.time.Instant;

@RestController
@RequestMapping("/api/v1.0/blogsite/blogs")
public class BlogQueryController {
    private final BlogQueryService blogQueryService;

    public BlogQueryController(BlogQueryService blogQueryService) {
        this.blogQueryService = blogQueryService;
    }

    // Fetches the blog based on the given category
    // GET /api/v1.0/blogsite/blogs/info/{category}
    @GetMapping("/info/{category}")
    public ResponseEntity<BlogSearchResponse> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(blogQueryService.searchByCategory(category));
    }

    // Search by category & filter by date range
    // GET /api/v1.0/blogsite/blogs/get/{category}/{fromDate}/{toDate}
    @GetMapping("/get/{category}/{fromDate}/{toDate}")
    public ResponseEntity<BlogSearchResponse> getByCategoryAndDateRange(
            @PathVariable String category,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = toDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return ResponseEntity.ok(blogQueryService.searchByCategoryAndRange(category, from, to));
    }

}