package com.example.backendservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponseDto {

    private String createdDate;

    private String lastModifiedDate;

    private String id;

    private String message;

    private String senderId;

    private String senderUsername;

    private String receiverId;

    private String receiverUsername;

}
