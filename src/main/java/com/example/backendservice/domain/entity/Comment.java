package com.example.backendservice.domain.entity;

import com.example.backendservice.domain.entity.common.UserDateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "comments")
public class Comment extends UserDateAuditing {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Nationalized
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int level;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_COMMENT_USER"))
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_COMMENT_POST"))
    private Post post;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> replies;

}
