package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    @Query(value = "SELECT * FROM comments WHERE post_id = ?1", nativeQuery = true)
    Page<Comment> getAllCommentByPostId(String postId, Pageable pageable);
    @Query(value = "SELECT * FROM comments WHERE parent_comment_id = ?1", nativeQuery = true)
    Page<Comment> getAllReplyByParentCommentId(String parentCommentId, Pageable pageable);
}
