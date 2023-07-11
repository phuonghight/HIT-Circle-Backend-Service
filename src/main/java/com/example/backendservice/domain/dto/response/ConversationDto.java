package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConversationDto extends DateAuditingDto {

    private String id;

    private String firstUserId;

    private String secondUserId;

}
