package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequestDto {

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;
}
