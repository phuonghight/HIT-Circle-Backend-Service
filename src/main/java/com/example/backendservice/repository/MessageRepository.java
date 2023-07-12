package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    @Query(value = "SELECT * FROM messages m WHERE m.conversation_id = ?1", nativeQuery = true)
    Page<Message> getMessagesByConversationId(String conversationId, Pageable pageable);
}
