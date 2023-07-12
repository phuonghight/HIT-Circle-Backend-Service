package com.example.backendservice.service.impl;

import com.example.backendservice.constant.SortByDataConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.pagination.PagingMeta;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;
import com.example.backendservice.domain.entity.Conversation;
import com.example.backendservice.domain.entity.Message;
import com.example.backendservice.domain.mapper.MessageMapper;
import com.example.backendservice.repository.MessageRepository;
import com.example.backendservice.service.ConversationService;
import com.example.backendservice.service.MessageService;
import com.example.backendservice.util.DateTimeUtil;
import com.example.backendservice.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final ConversationService conversationService;

    @Override
    public MessageResponseDto creatNewMessage(MessageRequestDto messageRequestDto) {
        Conversation conversation = conversationService
                .getConversationById(messageRequestDto.getConversationId());

//        if (!(conversation.getFirstUser().getId().equals(messageRequestDto.getSenderId())
//        && conversation.getSecondUser().getId().equals(messageRequestDto.getReceiverId()))
//        || !(conversation.getFirstUser().getId().equals(messageRequestDto.getReceiverId())
//                && conversation.getSecondUser().getId().equals(messageRequestDto.getSenderId()))) {
//            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
//        }

        Message message = messageMapper.toMessage(messageRequestDto);
        message.setConversation(conversation);
        messageRepository.save(message);

        MessageResponseDto messageResponseDto = messageMapper.toResponseDto(message);
        messageResponseDto.setCreatedDate(DateTimeUtil.toString(message.getCreatedDate()));
        messageResponseDto.setLastModifiedDate(DateTimeUtil.toString(message.getLastModifiedDate()));

        return messageResponseDto;
    }

    @Override
    public PaginationResponseDto<MessageResponseDto> getMessagesByConversationId(
            PaginationFullRequestDto paginationFullRequestDto, String conversationId) {
        paginationFullRequestDto.setIsAscending(true);
        Pageable pageable = PaginationUtil
                .buildPageable(paginationFullRequestDto, SortByDataConstant.Message);

        Page<Message> messagePage = messageRepository
                .getMessagesByConversationId(conversationId, pageable);

        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.Message, messagePage);

        List<MessageResponseDto> messageResponseDtoList =
                messageMapper.toResponseDto(messagePage.getContent());

        return new PaginationResponseDto<>(meta, messageResponseDtoList);
    }
}
