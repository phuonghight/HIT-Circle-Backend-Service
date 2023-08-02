package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    @Query(value = "SELECT * FROM messages s WHERE (s.sender_id=?1 AND s.receiver_id=?2) " +
            "OR (s.receiver_id=?1 AND s.sender_id=?2) ORDER BY s.created_date ASC", nativeQuery = true)
    List<Message> getMessagesBySenderIdAndReceiverId(String senderId, String receiverId);
}
