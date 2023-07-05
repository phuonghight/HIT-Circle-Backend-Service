package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {

    @Query("SELECT f FROM Follow f WHERE f.from.id=?1 and f.to.id=?2")
    Optional<Follow> findFollowByFromAndTo(String fromUserId, String toUserId);
}
