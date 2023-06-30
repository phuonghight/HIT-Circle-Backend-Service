package com.example.backendservice.service;

import com.example.backendservice.domain.dto.request.ForgotPasswordRequestDto;
import com.example.backendservice.domain.dto.request.LoginRequestDto;
import com.example.backendservice.domain.dto.request.TokenRefreshRequestDto;
import com.example.backendservice.domain.dto.request.UserCreateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.LoginResponseDto;
import com.example.backendservice.domain.dto.response.TokenRefreshResponseDto;
import com.example.backendservice.domain.dto.response.UserDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto request);

  TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

  UserDto register(UserCreateDto userCreateDto);

  CommonResponseDto logout(HttpServletRequest request,
                           HttpServletResponse response, Authentication authentication);

  CommonResponseDto forgotPassword(ForgotPasswordRequestDto requestDto);
}
