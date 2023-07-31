package com.example.backendservice.service;

import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.request.ChangePasswordRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationSortRequestDto;
import com.example.backendservice.domain.dto.request.FollowRequestDto;
import com.example.backendservice.domain.dto.request.UserUpdateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.UserDto;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.security.UserPrincipal;

public interface UserService {

  UserDto getUserDtoById(String userId);

  User getUserById(String userId);

  PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request);

  UserDto getCurrentUser(UserPrincipal principal);

  UserDto updateProfile(String userId, UserUpdateDto userUpdateDto);

  CommonResponseDto changePassword(String userId, ChangePasswordRequestDto passwordRequestDto);

  PaginationResponseDto<UserDto> getFollowers(PaginationRequestDto paginationRequestDto,
                                              String username);

  PaginationResponseDto<UserDto> getFollowing(PaginationRequestDto paginationRequestDto,
                                              String username);

  PaginationResponseDto<UserDto> getFriendsById(PaginationFullRequestDto paginationFullRequestDto, String meId);

}
