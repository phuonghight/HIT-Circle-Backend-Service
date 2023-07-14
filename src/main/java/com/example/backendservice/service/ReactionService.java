package com.example.backendservice.service;

import com.example.backendservice.domain.dto.request.ReactionRequestDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.ReactionDto;
import com.example.backendservice.domain.entity.Reaction;

import java.util.List;

public interface ReactionService {
    ReactionDto react(String userId , ReactionRequestDto reactionRequestDto);
    CommonResponseDto removeReact(String userId, String postId);
    ReactionDto findReactionByUserIdAndPostId(String userId, String postId);
    List<ReactionDto> findAllReactionByPostId(String postId);
    List<ReactionDto> findAllReactionByReactionName(ReactionRequestDto reactionRequestDto);
}
