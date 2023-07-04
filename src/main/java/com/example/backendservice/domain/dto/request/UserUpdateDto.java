package com.example.backendservice.domain.dto.request;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.validator.annotation.ValidDate;
import com.example.backendservice.validator.annotation.ValidFileImage;
import com.example.backendservice.validator.annotation.ValidGender;
import com.example.backendservice.validator.annotation.ValidStudentCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {

  @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
          message = ErrorMessage.INVALID_FORMAT_EMAIL)
  private String email;

  private String fullName;

  @ValidGender
  private String gender;

  @ValidFileImage
  private MultipartFile avatar;

  @ValidDate
  private String birthday;

  @Pattern(regexp = "^0\\d{9}$", message = ErrorMessage.INVALID_FORMAT_PHONE)
  private String phone;

}
