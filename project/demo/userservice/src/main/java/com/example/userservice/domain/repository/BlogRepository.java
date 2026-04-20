package com.example.userservice.domain.repository;

        import com.example.userservice.domain.model.Blog;
        import com.example.userservice.domain.model.User;
        import org.springframework.data.jpa.repository.*;
        import org.springframework.data.repository.query.Param;

        import java.util.List;
        import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("SELECT b FROM Blog b WHERE b.owner = :owner")
    List<Blog> findAllByOwner(@Param("owner") User owner);

    @Query("SELECT b FROM Blog b WHERE b.owner = :owner AND b.blogName = :blogName")
    Optional<Blog> findByOwnerAndBlogName(@Param("owner") User owner, @Param("blogName") String blogName);
}