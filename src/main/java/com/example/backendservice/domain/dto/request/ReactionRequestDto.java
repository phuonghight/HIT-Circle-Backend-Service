package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.validator.annotation.ValidReaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReactionRequestDto {
    @ValidReaction
    private String name;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String postId;
}
