package com.example.backendservice.domain.entity;

import com.example.backendservice.domain.entity.common.UserDateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "postmedias")
public class PostMedia extends UserDateAuditing {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;
    @Column(nullable = true)
    private String mediaFile;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_POSTMEDIA_POST"))
    private Post post;
}
