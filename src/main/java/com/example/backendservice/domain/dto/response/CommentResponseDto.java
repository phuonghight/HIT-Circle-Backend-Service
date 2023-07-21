package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDto {
    private String id;
    private String comment;
    private int level;
    private String userId;
    private String postId;
    private String parentCommentId;
    private String createdBy;
    private String lastModifiedBy;
    private String createdDate;
    private String lastModifiedDate;
}
