package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.validator.annotation.ValidPhone;
import com.example.backendservice.validator.annotation.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  @Email(regexp = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",
          message = ErrorMessage.INVALID_FORMAT_EMAIL)
  private String email;

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
  private String password;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String fullName;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @ValidUsername
  private String username;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @ValidPhone
  private String phone;

}
