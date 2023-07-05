package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.validator.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {

  @Email(regexp = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",
          message = ErrorMessage.INVALID_FORMAT_EMAIL)
  private String email;

  private String fullName;

  @ValidUsername
  private String username;

  @ValidPhone
  private String phone;

  @ValidGender
  private String gender;

  @ValidFileImage
  private MultipartFile avatar;

  @ValidDate
  private String birthday;

}
