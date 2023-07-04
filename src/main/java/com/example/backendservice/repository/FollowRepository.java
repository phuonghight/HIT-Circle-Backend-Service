package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {

    @Query("SELECT f FROM Follow f WHERE f.from.id=?1 and f.to.id=?2")
    Optional<Follow> findFollowByFromIdAndToId(String fromUserId, String toUserId);

    @Query("SELECT f FROM Follow f WHERE  f.to.id=?1")
    Page<Follow> findFollowsByToId(String toUserId, Pageable pageable);

    @Query("SELECT f FROM Follow f WHERE  f.from.id=?1")
    Page<Follow> findFollowsByFromId(String fromUserId, Pageable pageable);

}
