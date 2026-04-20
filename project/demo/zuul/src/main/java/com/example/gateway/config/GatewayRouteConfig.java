package com.example.gateway.config;

import com.example.gateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,
                               AuthenticationFilter authFilter) {
        return builder.routes()
                // USER SERVICE (auth endpoints only)
                .route("user-service-auth", r -> r
                        .path("/api/v1.0/blogsite/user/auth/**")
                        // Uncomment if you want to apply your custom filter
                        // .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8091")
                )
                // BLOG SERVICE (user blogs CRUD)
                .route("blog-service-user", r -> r
                        .path("/api/v1.0/blogsite/user/blogs/**")
                        // Uncomment if you want to apply your custom filter
                        // .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8090")
                )
                // BLOG SERVICE (public blogs)
                .route("blog-service-public", r -> r
                        .path("/api/v1.0/blogsite/blogs/**")
                        // Uncomment if you want to apply your custom filter
                        // .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8090")
                )
                .build();
    }
}
