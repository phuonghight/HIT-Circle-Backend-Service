package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.entity.common.UserDateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReactionDto extends UserDateAuditing {
    private String id;
    private String name;
    private String userId;
    private String postId;
}
