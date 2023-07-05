package com.example.backendservice.domain.entity;

import com.example.backendservice.domain.entity.common.UserDateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "follows")
public class Follow extends UserDateAuditing {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name="user_follower_id", foreignKey = @ForeignKey(name = "FK_USER_FOLLOWER"))
    private User from;

    @ManyToOne
    @JoinColumn(name="user_following_id", foreignKey = @ForeignKey(name = "FK_USER_FOLLOWING"))
    private User to;

}
