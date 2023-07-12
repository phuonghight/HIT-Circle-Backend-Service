package com.example.backendservice.domain.entity;

import com.example.backendservice.domain.entity.common.DateAuditing;
import com.example.backendservice.domain.entity.common.UserDateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "messages")
public class Message extends DateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Nationalized
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String receiverId;

    @ManyToOne
    @JoinColumn(name = "conversation_id", foreignKey = @ForeignKey(name = "FK_MESSAGE_CONVERSATION"))
    private Conversation conversation;

}
