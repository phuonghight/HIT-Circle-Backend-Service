package com.example.backendservice.service.impl;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.RoleConstant;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.domain.dto.common.DataMailDto;
import com.example.backendservice.domain.dto.request.ForgotPasswordRequestDto;
import com.example.backendservice.domain.dto.request.LoginRequestDto;
import com.example.backendservice.domain.dto.request.TokenRefreshRequestDto;
import com.example.backendservice.domain.dto.request.UserCreateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.LoginResponseDto;
import com.example.backendservice.domain.dto.response.TokenRefreshResponseDto;
import com.example.backendservice.domain.dto.response.UserDto;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.UserMapper;
import com.example.backendservice.exception.AlreadyExistException;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.exception.UnauthorizedException;
import com.example.backendservice.repository.RoleRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.security.jwt.JwtTokenProvider;
import com.example.backendservice.service.AuthService;
import com.example.backendservice.util.RandomString;
import com.example.backendservice.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final SendMailUtil sendMailUtil;

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
      String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
      return new LoginResponseDto(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());
    } catch (InternalAuthenticationServiceException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
    }
  }

  @Override
  public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
    return null;
  }

  @Override
  public UserDto register(UserCreateDto userCreateDto) {
    Optional<User> findUserByUsername = userRepository.findByUsername(userCreateDto.getUsername());
    Optional<User> findUserByStudentCode = userRepository.findUserByStudentCode(userCreateDto.getStudentCode());

    if(!ObjectUtils.isEmpty(findUserByUsername)) {
      throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"email: " + userCreateDto.getUsername()});
    }
    if(!ObjectUtils.isEmpty(findUserByStudentCode)) {
      throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"student code: " + userCreateDto.getStudentCode()});
    }

    User user = userMapper.toUser(userCreateDto);
    user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
    user.setRole(roleRepository.findByRoleName(RoleConstant.USER));
//    user.setAvatar(CommonConstant.DEFAULT_AVATAR);

    return userMapper.toUserDto(userRepository.save(user));
  }

  @Override
  public CommonResponseDto logout(HttpServletRequest request,
                                  HttpServletResponse response, Authentication authentication) {
    new SecurityContextLogoutHandler().logout(request, response, authentication);
    return new CommonResponseDto(true, MessageConstant.SUCCESSFULLY_LOGOUT);
  }

  @Override
  public CommonResponseDto forgotPassword(ForgotPasswordRequestDto requestDto) {
    User user = userRepository.findByUsername(requestDto.getEmail())
            .orElseThrow(() -> {
              throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                      new String[]{requestDto.getEmail()});
            });

    String newPassword = RandomString.generate(CommonConstant.RANDOM_PASSWORD_LENGTH);

    Map<String, Object> props = new HashMap<>();
    props.put("fullName", user.getFullName());
    props.put("password", newPassword);
    props.put("appName", CommonConstant.APP_NAME);

    DataMailDto mail = new DataMailDto(user.getUsername(),
            MessageConstant.SUBJECT_MAIL_RESET_PASSWORD, null, props);

    try {
      sendMailUtil.sendEmailWithHTML(mail, "reset-password.html");
    } catch (Exception e) {
      log.error("Send mail failed for {}", e.getMessage());
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    return new CommonResponseDto(true, MessageConstant.RESET_PASSWORD);
  }

}
