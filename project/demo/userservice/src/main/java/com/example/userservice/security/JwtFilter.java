package com.example.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, javax.servlet.ServletException {
        String uri = request.getRequestURI();

        // ✅ Allow login, register, and Swagger/OpenAPI endpoints without JWT
        if (uri.contains("/login") || uri.contains("/register")
                || uri.contains("/swagger-ui") || uri.contains("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();

                // TODO: Optionally set authentication context here
                // Example:
                // UsernamePasswordAuthenticationToken auth =
                //     new UsernamePasswordAuthenticationToken(
                //         claims.getSubject(), null,
                //         Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                // SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
