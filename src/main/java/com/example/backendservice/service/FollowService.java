package com.example.backendservice.service;

import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.FollowResponseDto;

public interface FollowService {

    FollowResponseDto follow(String currentUserId, String toUserId);

    CommonResponseDto unfollow(String currentUserId, String toUserId);

}
