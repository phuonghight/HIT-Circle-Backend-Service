package com.example.backendservice.service;

import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.response.ConversationDto;
import com.example.backendservice.domain.entity.Conversation;

public interface ConversationService {

    Conversation getConversationById(String id);

    ConversationDto getConversationDtoById(String id);

    ConversationDto getConversationByMeAndSecondUserId(String currentUserId, String secondUserId);

    ConversationDto creatNewConversation(String firstUserId, String secondUserId);

    PaginationResponseDto<ConversationDto> getConversationsOfMe(String userId,
                                                                PaginationFullRequestDto paginationFullRequestDto);

}
