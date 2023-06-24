package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.request.UserCreateDto;
import com.example.backendservice.domain.dto.response.UserDto;
import com.example.backendservice.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserCreateDto userCreateDTO);

  @Mappings({
      @Mapping(target = "roleName", source = "user.role.name"),
  })
  UserDto toUserDto(User user);

  List<UserDto> toUserDtos(List<User> user);

}
