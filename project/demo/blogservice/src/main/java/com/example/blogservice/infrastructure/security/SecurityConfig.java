package com.example.blogservice.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Allow all GET requests to blogs
                .antMatchers(HttpMethod.GET, "/api/v1.0/blogsite/blogs/**").permitAll()
                // Allow USERs and ADMINs to add blogs
                .antMatchers(HttpMethod.POST, "/api/v1.0/blogsite/user/blogs/**").hasAnyRole("USER", "ADMIN")
                // Only ADMIN role can access admin endpoints
                .antMatchers(HttpMethod.POST, "/api/v1.0/blogsite/admin/**").hasRole("ADMIN")
                // Everything else requires authentication
                .anyRequest().authenticated()
                .and()
                .httpBasic(); // Basic Auth for testing

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define users explicitly
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .roles("ADMIN")
                        .build(),
                User.withUsername("user")
                        .password(passwordEncoder.encode("user123"))
                        .roles("USER")
                        .build()
        );
    }
}
