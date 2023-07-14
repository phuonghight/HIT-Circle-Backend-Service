package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, String> {
    @Query(value = "SELECT * FROM reactions u WHERE u.user_id = ?1 AND u.post_id = ?2", nativeQuery = true)
    Reaction findByUserIdAndPostId(String userId, String postId);
    @Query(value = "SELECT * FROM reactions u WHERE u.post_id = ?1", nativeQuery = true)
    List<Reaction> findAllByPostId(String postId);
    @Query(value = "SELECT * FROM reactions u WHERE u.name = ?1 AND u.post_id = ?2", nativeQuery = true)
    List<Reaction> findAllByReactionName(String name, String postId);
}
