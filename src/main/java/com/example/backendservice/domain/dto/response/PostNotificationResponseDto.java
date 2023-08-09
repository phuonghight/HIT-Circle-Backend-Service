package com.example.backendservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostNotificationResponseDto {
    private String id;
    private String notificationMessage;
    private String username;
    private String avatar;
    private String createdBy;
    private String lastModifiedBy;
    private String createdDate;
    private String lastModifiedDate;
}
