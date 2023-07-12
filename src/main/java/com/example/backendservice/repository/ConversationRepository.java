package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

    @Query(value = "SELECT * FROM conversations c WHERE c.id = ?1", nativeQuery = true)
    Optional<Conversation> getConversationById(String id);

    @Query(value = "SELECT c.* FROM conversations c " +
            "WHERE (c.first_user_id = ?1 AND c.second_user_id = ?2) " +
            "OR (c.first_user_id = ?2 AND c.second_user_id = ?1)", nativeQuery = true)
    Optional<Conversation> getConversationByMeAndSecondUserId(String myUsername, String secondUserUsername);

    @Query(value = "SELECT * FROM conversations c WHERE c.first_user_id = ?1 OR c.second_user_id = ?1",
            nativeQuery = true)
    Page<Conversation> getConversationsByUserId(String userId, Pageable pageable);
}
