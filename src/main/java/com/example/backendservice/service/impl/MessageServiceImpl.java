package com.example.backendservice.service.impl;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.SortByDataConstant;
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
        paginationFullRequestDto.setIsAscending(true);
        paginationFullRequestDto.setPageSize(CommonConstant.NUM_OF_MESSAGES_PER_PAGE_DEFAULT);

        Pageable pageable = PaginationUtil
                .buildPageable(paginationFullRequestDto, SortByDataConstant.Message);

        Page<Message> messagePage = messageRepository
                .getMessagesBySenderIdAndReceiverId(meId, otherId, pageable);

        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.Message, messagePage);

        List<MessageResponseDto> messageResponseDtoList =
                messageMapper.toResponseDto(messagePage.getContent());

        return new PaginationResponseDto<>(meta, messageResponseDtoList);
    }
}
