package com.example.backendservice.service;

import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;

public interface MessageService {

    MessageResponseDto sendMessageToOtherById(String meId, MessageRequestDto messageRequestDto);

    PaginationResponseDto<MessageResponseDto> getMessagesBySenderIdAndReceiverId(
            PaginationFullRequestDto paginationFullRequestDto,
            String meId, String otherId);

}
