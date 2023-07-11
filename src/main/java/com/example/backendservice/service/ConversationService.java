package com.example.backendservice.service;

import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.response.ConversationDto;

public interface ConversationService {

    ConversationDto getConversationById(String id, String userId);

    ConversationDto getConversationByMeAndSecondUserName(String currentUserId, String secondUserId);

    ConversationDto creatNewConversation(String firstUserId, String secondUserId);

    PaginationResponseDto<ConversationDto> getConversationsOfMe(String userId,
                                                                PaginationFullRequestDto paginationFullRequestDto);

}
