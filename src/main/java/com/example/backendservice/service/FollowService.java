package com.example.backendservice.service;

import com.example.backendservice.domain.dto.request.FollowRequestDto;
import com.example.backendservice.domain.dto.response.FollowResponseDto;

public interface FollowService {

    FollowResponseDto follow(String currentUserId, String toUserId);

    FollowResponseDto unfollow(String currentUserId, String toUserId);

}
