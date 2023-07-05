package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    @Query(value = "SELECT * FROM posts u ORDER BY u.created_date DESC", nativeQuery = true)
    List<Post> findAllPost();
    @Query(value = "SELECT * FROM posts u WHERE u.user_id = ?1 ORDER BY u.created_date DESC", nativeQuery = true)
    List<Post> findAllPostByUserId(String userId);
}
