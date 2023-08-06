package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto extends UserDateAuditingDto {
    private String id;
    private String caption;
    private String userId;
    private String username;
    private String avatar;
    private List<String> mediaFiles;
}
