package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostMediaDto extends UserDateAuditingDto {
    private String id;
    private String mediaFile;
    private String postId;
}
