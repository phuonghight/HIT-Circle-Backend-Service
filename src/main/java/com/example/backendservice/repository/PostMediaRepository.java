package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostMediaRepository extends JpaRepository<PostMedia, String> {
    @Modifying
    @Query(value = "DELETE FROM postmedias u WHERE u.post_id = ?1", nativeQuery = true)
    void deletePosMediaByPostId(String postId);
    @Modifying
    @Query(value = "SELECT * FROM postmedias u WHERE u.post_id = ?1", nativeQuery = true)
    List<PostMedia> findAllPostMediaByPostId(String postId);
}
