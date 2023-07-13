package com.example.backendservice.repository;

import com.example.backendservice.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    @Query(value = "SELECT * FROM messages s WHERE s.sender_id=?1 AND s.receiver_id=?2", nativeQuery = true)
    Page<Message> getMessagesBySenderIdAndReceiverId(String senderId, String receiverId, Pageable pageable);
}
