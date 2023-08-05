package com.example.backendservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentNotificationResponseDto {
    private String id;
    private String notificationMessage;
    private String usernameCreatedComment;
    private String postId;
    private String parentCommentId;
    private String createdBy;
    private String lastModifiedBy;
    private String createdDate;
    private String lastModifiedDate;
}
