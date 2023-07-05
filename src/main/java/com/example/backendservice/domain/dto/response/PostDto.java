package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.entity.common.UserDateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto extends UserDateAuditing {
    private String id;
    private String caption;
    private String userId;
    private List<String> mediaFiles;
}
