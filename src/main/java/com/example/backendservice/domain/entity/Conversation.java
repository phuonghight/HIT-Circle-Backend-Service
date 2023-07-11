package com.example.backendservice.domain.entity;

import com.example.backendservice.domain.entity.common.DateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "conversations")
public class Conversation extends DateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "first_user_id", foreignKey = @ForeignKey(name = "FK_CONVERSATION_USER1"))
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user_id", foreignKey = @ForeignKey(name = "FK_CONVERSATION_USER2"))
    private User secondUser;

    public Conversation(User firstUser, User secondUser) {
        this.firstUser =  firstUser;
        this.secondUser = secondUser;
    }
}
