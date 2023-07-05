package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.RoleConstant;
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
import com.example.backendservice.exception.UnauthorizedException;
import com.example.backendservice.repository.RoleRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.security.jwt.JwtTokenProvider;
import com.example.backendservice.service.AuthService;
import com.example.backendservice.util.CheckLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    try {
      Authentication authentication = null;
      if (CheckLoginRequest.isPhone(request.getAccount())) {
        Optional<User> user = userRepository.findUserByPhone(request.getAccount());
        if (user.isPresent()) {
          authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(user.get().getEmail(), request.getPassword()));
        } else throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_ACCOUNT);
      } else if (CheckLoginRequest.isEmail(request.getAccount())) {
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getAccount(), request.getPassword()));
      }

      SecurityContextHolder.getContext().setAuthentication(authentication);
      assert authentication != null;
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
      String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
      return new LoginResponseDto(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());
    } catch (InternalAuthenticationServiceException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_ACCOUNT);
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
    Optional<User> findUserByEmail = userRepository.findUserByUsername(userCreateDto.getEmail());
    Optional<User> findUserByUsername = userRepository.findUserByUsername(userCreateDto.getUsername());

    if(findUserByEmail.isPresent()) {
      throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"email: " + userCreateDto.getEmail()});
    }

    if(findUserByUsername.isPresent()) {
      throw new AlreadyExistException(ErrorMessage.User.ERR_ALREADY_EXIST_USER,
              new String[]{"username: " + userCreateDto.getUsername()});
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
    return new CommonResponseDto(true, "Successfully Logout");
  }

}
