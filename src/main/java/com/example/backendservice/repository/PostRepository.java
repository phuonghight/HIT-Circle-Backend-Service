package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    @Query(value = "SELECT posts.* FROM posts " +
            "WHERE posts.user_id IN ( " +
            "SELECT follows.user_following_id FROM follows " +
            "WHERE follows.user_follower_id = ?1 ) OR posts.user_id = ?1 " +
            "ORDER BY posts.created_date DESC", nativeQuery = true)
    List<Post> findAllPost(String userId);
    @Query(value = "SELECT * FROM posts u WHERE u.user_id = ?1 ORDER BY u.created_date DESC", nativeQuery = true)
    List<Post> findAllPostByUserId(String userId);
}
