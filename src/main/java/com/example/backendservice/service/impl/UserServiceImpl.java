package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.SortByDataConstant;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.domain.dto.pagination.*;
import com.example.backendservice.domain.dto.request.FollowRequestDto;
import com.example.backendservice.domain.dto.pagination.*;
import com.example.backendservice.domain.dto.request.ChangePasswordRequestDto;
import com.example.backendservice.domain.dto.request.UserUpdateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.UserDto;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.UserMapper;
import com.example.backendservice.exception.AlreadyExistException;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.repository.FollowRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.UserService;
import com.example.backendservice.util.PaginationUtil;
import com.example.backendservice.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final UploadFileUtil uploadFileUtil;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto getUserDtoById(String userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
    return userMapper.toUserDto(user);
  }

  @Override
  public User getUserById(String userId) {
    return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
  }

  @Override
  public PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request) {
    //Pagination
    Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.USER);
    //Create Output
    return new PaginationResponseDto<>(null, null);
  }

  @Override
  public UserDto getCurrentUser(UserPrincipal principal) {
    User user = userRepository.getUser(principal);
    return userMapper.toUserDto(user);
  }

  @Override
  public UserDto updateProfile(String userId, UserUpdateDto userUpdateDto) {
    User user = this.getUserById(userId);

    userMapper.updateUser(user, userUpdateDto);

    if (userUpdateDto.getEmail() != null) {
      if (!userRepository.existsByEmail(userUpdateDto.getEmail())) {
        user.setEmail(userUpdateDto.getEmail());
      } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"email: " + userUpdateDto.getEmail()});
    }

    if (userUpdateDto.getUsername() != null) {
      if (!userRepository.existsByUsername(userUpdateDto.getUsername())) {
        user.setUsername(userUpdateDto.getUsername());
      } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"username: " + userUpdateDto.getUsername()});
    }

    if (userUpdateDto.getPhone() != null) {
      if (!userRepository.existsByPhone(userUpdateDto.getPhone())) {
        user.setPhone(userUpdateDto.getPhone());
      } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"phone: " + userUpdateDto.getPhone()});
    }

    if (userUpdateDto.getAvatar() != null) {
      String oldAvatar = user.getAvatar();
      if (oldAvatar != null) {
        uploadFileUtil.destroyFileWithUrl(oldAvatar);
      }
      String newAvatar = uploadFileUtil.uploadFile(userUpdateDto.getAvatar());
      user.setAvatar(newAvatar);
    }

    return userMapper.toUserDto(userRepository.save(user));
  }

  @Override
  public CommonResponseDto changePassword(String userId, ChangePasswordRequestDto passwordRequestDto) {
    User user = this.getUserById(userId);

    if (!passwordEncoder.matches(passwordRequestDto.getCurrentPassword(), user.getPassword())) {
      return new CommonResponseDto(false, MessageConstant.CURRENT_PASSWORD_INCORRECT);
    }

    if (passwordRequestDto.getCurrentPassword().equals(passwordRequestDto.getNewPassword())) {
      return new CommonResponseDto(false, MessageConstant.SAME_PASSWORD);
    }

    user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
    userRepository.save(user);

    return new CommonResponseDto(true, MessageConstant.CHANGE_PASSWORD_SUCCESSFULLY);
  }

  @Override
  public PaginationResponseDto<UserDto> getFollowers(PaginationRequestDto paginationRequestDto, String userId) {
    List<User> users = userRepository.getFollowersByUserId(userId);
    return getUserDtoPaginationResponseDto(paginationRequestDto, users);
  }

  @Override
  public PaginationResponseDto<UserDto> getFollowing(PaginationRequestDto paginationRequestDto, String userId) {
    List<User> users = userRepository.getFollowingByUserId(userId);
    return getUserDtoPaginationResponseDto(paginationRequestDto, users);
  }

  @Override
  public PaginationResponseDto<UserDto> getFriendsById(PaginationFullRequestDto paginationFullRequestDto, String meId) {
    List<User> userPage = userRepository.getFriendsById(meId);
    return getUserDtoPaginationResponseDto(paginationFullRequestDto, userPage);
  }

  @Override
  public PaginationResponseDto<UserDto> getConversations(
          PaginationFullRequestDto paginationFullRequestDto, String meId
  ) {
    Set<User> users = userRepository.getConversation(meId);
    return getUserDtoPaginationResponseDto(paginationFullRequestDto, new ArrayList<>(users));
  }

  public PaginationResponseDto<UserDto> searchUserByUsername(
          PaginationRequestDto paginationRequestDto,
          String q,
          String meId
  ) {
    List<User> followingByName = userRepository.getFollowingByName(meId, q);
    if (followingByName.size() >= paginationRequestDto.getPageSize())
      return this.getUserDtoPaginationResponseDto(paginationRequestDto, followingByName);
    else {
      List<User> users = userRepository.getUsersByNameNotFollowing(meId, q);
      users.addAll(0, followingByName);

      return this.getUserDtoPaginationResponseDto(paginationRequestDto, users);
    }
  }

  private PaginationResponseDto<UserDto> getUserDtoPaginationResponseDto(PaginationRequestDto paginationRequestDto, List<User> users) {
    List<User> result = new ArrayList<>();

    int pageSize = paginationRequestDto.getPageSize();
    int pageNum = paginationRequestDto.getPageNum() + 1;
    long totalElements = users.size();
    int totalPages;
    if (users.size() % pageSize == 0) totalPages = users.size() / paginationRequestDto.getPageSize();
    else totalPages = users.size() / paginationRequestDto.getPageSize() + 1;

    long n = Math.min((long) pageNum * pageSize, totalElements);
    for (int i = pageNum * pageSize - pageSize; i < n ; i++) {
      result.add(users.get(i));
    }

    PagingMeta meta = new PagingMeta(
            totalElements,
            totalPages,
            pageNum,
            pageSize,
            "created_date",
            "DESC"
    );

    return new PaginationResponseDto<>(meta, userMapper.toUserDtos(result));
  }

}
