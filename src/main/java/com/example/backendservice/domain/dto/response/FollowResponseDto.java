package com.example.backendservice.domain.dto.response;

import com.example.backendservice.domain.dto.common.UserDateAuditingDto;
import com.example.backendservice.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowResponseDto extends UserDateAuditingDto {

    private String id;

    private User from;

    private User to;

}
