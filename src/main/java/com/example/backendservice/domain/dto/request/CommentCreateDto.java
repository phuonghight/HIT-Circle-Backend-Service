package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentCreateDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String comment;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String postId;
    private String parentCommentId;
}
