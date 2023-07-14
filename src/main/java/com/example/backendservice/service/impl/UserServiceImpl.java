package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.SortByDataConstant;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.pagination.PaginationSortRequestDto;
import com.example.backendservice.domain.dto.pagination.PagingMeta;
import com.example.backendservice.domain.dto.request.FollowRequestDto;
import com.example.backendservice.domain.dto.request.ChangePasswordRequestDto;
import com.example.backendservice.domain.dto.request.UserUpdateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.UserDto;
import com.example.backendservice.domain.entity.Follow;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final FollowRepository followRepository;

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
  public PaginationResponseDto<UserDto> getFollowers(PaginationSortRequestDto paginationSortRequestDto,
                                                     FollowRequestDto followRequestDto) {
    Pageable pageable = PaginationUtil.buildPageable(paginationSortRequestDto, SortByDataConstant.Follow);

    Page<Follow> followPage = followRepository.findFollowsByToId(followRequestDto.getUserId(), pageable);

    List<Follow> follows = followPage.getContent();

    List<User> followers = new ArrayList<>();

    for (Follow follow : follows) {
      followers.add(this.getUserById(follow.getFrom().getId()));
    }

    PagingMeta meta = PaginationUtil.buildPagingMeta(paginationSortRequestDto,
            SortByDataConstant.Follow,
            followPage);

    return new PaginationResponseDto<>(meta, userMapper.toUserDtos(followers));
  }

  @Override
  public PaginationResponseDto<UserDto> getFollowing(PaginationSortRequestDto paginationSortRequestDto,
                                                     FollowRequestDto followRequestDto) {
    Pageable pageable = PaginationUtil.buildPageable(paginationSortRequestDto, SortByDataConstant.Follow);

    Page<Follow> followPage = followRepository.findFollowsByFromId(followRequestDto.getUserId(), pageable);

    List<Follow> follows = followPage.getContent();

    List<User> following = new ArrayList<>();

    for (Follow follow : follows) {
      following.add(this.getUserById(follow.getTo().getId()));
    }

    PagingMeta meta = PaginationUtil.buildPagingMeta(paginationSortRequestDto,
            SortByDataConstant.Follow,
            followPage);

    return new PaginationResponseDto<>(meta, userMapper.toUserDtos(following));
  }

  @Override
  public PaginationResponseDto<UserDto> getFriendsById(PaginationFullRequestDto paginationFullRequestDto, String meId) {

    Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.USER);

    Page<User> userPage = userRepository.getFriendsById(meId, pageable);

    PagingMeta meta = PaginationUtil.buildPagingMeta(paginationFullRequestDto, SortByDataConstant.USER, userPage);

    return new PaginationResponseDto<>(meta, userMapper.toUserDtos(userPage.getContent()));
  }

}
