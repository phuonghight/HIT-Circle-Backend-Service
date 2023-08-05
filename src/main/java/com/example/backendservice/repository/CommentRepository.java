package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    @Query(value = "SELECT * FROM comments WHERE post_id = ?1 AND level = 1", nativeQuery = true)
    Page<Comment> getAllCommentByPostId(String postId, Pageable pageable);
    @Query(value = "SELECT * FROM comments WHERE parent_comment_id = ?1", nativeQuery = true)
    Page<Comment> getAllReplyByParentCommentId(String parentCommentId, Pageable pageable);
    @Query(value = "SELECT DISTINCT user_id FROM " +
            "( SELECT user_id FROM comments " +
            "WHERE (parent_comment_id = ?1 OR id = ?1) " +
            "UNION ALL " +
            "SELECT user_id FROM posts WHERE id = ?2) AS derivedTable " +
            "WHERE user_id <> ?3", nativeQuery = true)
    List<String> findAllUserIdNotification(String parentCommentId, String postId, String userId);
    @Query(value = "SELECT DISTINCT user_id FROM " +
            "(SELECT user_id FROM comments where post_id = ?1 AND level = 1 " +
            "UNION ALL " +
            "SELECT user_id FROM posts WHERE id = ?1) AS derivedTable " +
            "WHERE user_id <> ?2", nativeQuery = true)
    List<String> findAllUserIdNotification(String postId, String userId);
}
