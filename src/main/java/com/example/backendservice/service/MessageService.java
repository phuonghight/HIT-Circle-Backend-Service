package com.example.backendservice.service;

import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;

public interface MessageService {

    MessageResponseDto creatNewMessage(MessageRequestDto messageRequestDto);

    PaginationResponseDto<MessageResponseDto> getMessagesByConversationId(
            PaginationFullRequestDto paginationFullRequestDto, String conversationId);

}
