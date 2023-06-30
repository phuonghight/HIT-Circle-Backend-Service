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
public class PostDto extends DateAuditingDto {
    private String id;
    private String caption;
    private String user_id;
}
