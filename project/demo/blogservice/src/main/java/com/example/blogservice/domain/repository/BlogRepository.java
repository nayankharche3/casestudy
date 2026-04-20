package com.example.blogservice.domain.repository;

import com.example.blogservice.domain.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b FROM Blog b WHERE b.category = :category")
    List<Blog> findByCategory(@Param("category") String category);

    @Query("SELECT b FROM Blog b WHERE b.category = :category AND b.createdAt BETWEEN :from AND :to")
    List<Blog> findByCategoryAndDateRange(@Param("category") String category,
                                          @Param("from") Instant from,
                                          @Param("to") Instant to);
}