package com.example.backendservice.domain.dto.request;

import com.example.backendservice.validator.annotation.ValidReaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReactionRequestDto {
    @ValidReaction
    private String name;
    private String postId;
}
