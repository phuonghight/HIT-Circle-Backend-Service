package com.example.backendservice.service.impl;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.pagination.PagingMeta;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;
import com.example.backendservice.domain.entity.Message;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.MessageMapper;
import com.example.backendservice.exception.UnauthorizedException;
import com.example.backendservice.repository.MessageRepository;
import com.example.backendservice.service.MessageService;
import com.example.backendservice.service.UserService;
import com.example.backendservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final UserService userService;

    @Override
    public MessageResponseDto sendMessageToOtherById(String meId, MessageRequestDto messageRequestDto) {
        if (!meId.equals(messageRequestDto.getSenderId())) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }

        Message message = messageMapper.toMessage(messageRequestDto);
        User sender = userService.getUserById(meId);
        User receiver = userService.getUserById(messageRequestDto.getReceiverId());
        message.setSender(sender);
        message.setReceiver(receiver);
        messageRepository.save(message);

        MessageResponseDto messageResponseDto = messageMapper.toResponseDto(message);
        messageResponseDto.setCreatedDate(DateTimeUtil.toString(message.getCreatedDate()));
        messageResponseDto.setLastModifiedDate(DateTimeUtil.toString(message.getLastModifiedDate()));

        return messageResponseDto;
    }

    @Override
    public PaginationResponseDto<MessageResponseDto> getMessagesBySenderIdAndReceiverId(
            PaginationFullRequestDto paginationFullRequestDto,
            String meId, String otherId) {

        List<Message> messages = messageRepository.getMessagesBySenderIdAndReceiverId(meId, otherId);
        List<Message> result = new ArrayList<>();

        int pageNum = paginationFullRequestDto.getPageNum() + 1;
        int pageSize = paginationFullRequestDto.getPageSize() != CommonConstant.PAGE_SIZE_DEFAULT
                ? paginationFullRequestDto.getPageSize() : CommonConstant.NUM_OF_MESSAGES_PER_PAGE_DEFAULT;
        long totalElements = messages.size();
        int totalPages;
        if (messages.size() % pageSize == 0) totalPages = messages.size() / pageSize;
        else totalPages = messages.size() / pageSize + 1;

        long n = Math.min((long) pageNum * pageSize, totalElements);
        for (int i = pageNum * pageSize - pageSize; i < n ; i++) {
            result.add(messages.get(i));
        }

        PagingMeta meta = new PagingMeta(
                totalElements,
                totalPages,
                pageNum,
                pageSize,
                "created_date",
                "ASC"
        );

        return new PaginationResponseDto<>(meta, messageMapper.toResponseDto(result));
    }
}
