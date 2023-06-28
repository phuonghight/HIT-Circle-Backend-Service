package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.validator.annotation.ValidGender;
import com.example.backendservice.validator.annotation.ValidStudentCode;
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
  @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
          message = ErrorMessage.INVALID_FORMAT_EMAIL)
  private String username;

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
  private String password;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String fullName;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @ValidStudentCode(regexp = "^20\\d{8}$")
  private String studentCode;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @ValidGender
  private String gender;

}
