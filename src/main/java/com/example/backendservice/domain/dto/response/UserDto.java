package com.example.backendservice.domain.dto.response;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.domain.dto.common.DateAuditingDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends DateAuditingDto {

  private String id;

  private String email;

  private String fullName;

  private String gender;

  private String avatar;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstant.PATTERN_DATE)
  private LocalDate birthday;

  private String phone;

  private String roleName;

}

