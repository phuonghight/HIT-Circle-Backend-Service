package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.SortByDataConstant;
import com.example.backendservice.constant.SuccessMessage;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.request.ChangePasswordRequestDto;
import com.example.backendservice.domain.dto.request.UserUpdateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.UserDto;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.UserMapper;
import com.example.backendservice.exception.AlreadyExistException;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.exception.UnauthorizedException;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.UserService;
import com.example.backendservice.util.PaginationUtil;
import com.example.backendservice.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final UploadFileUtil uploadFileUtil;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

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

    if (userUpdateDto.getUsername() != null) {
      if (userRepository.findByUsername(userUpdateDto.getUsername()).isEmpty()) {
        user.setUsername(userUpdateDto.getUsername());
      } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"email: " + userUpdateDto.getUsername()});
    }

    if (userUpdateDto.getStudentCode() != null) {
      if (userRepository.findUserByStudentCode(userUpdateDto.getStudentCode()).isEmpty()) {
        user.setStudentCode(userUpdateDto.getStudentCode());
      } else throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"student code: " + userUpdateDto.getStudentCode()});
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

    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(user.getUsername(), passwordRequestDto.getOldPassword()));
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
    }

    user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
    userRepository.save(user);

    return new CommonResponseDto(true, SuccessMessage.SUCCESSFULLY_CHANGE_PASSWORD);
  }

}
