package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.SortByDataConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.pagination.PagingMeta;
import com.example.backendservice.domain.dto.response.ConversationDto;
import com.example.backendservice.domain.entity.Conversation;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.ConversationMapper;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.repository.ConversationRepository;
import com.example.backendservice.service.ConversationService;
import com.example.backendservice.service.UserService;
import com.example.backendservice.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    private final ConversationMapper conversationMapper;

    private final UserService userService;

    @Override
    public ConversationDto getConversationById(String id) {
        Conversation conversation = conversationRepository.getConversationById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.Conversation.ERR_NOT_FOUND, new String[]{"id: " + id});
                });

        return conversationMapper.toConversationDto(conversation);
    }

    @Override
    public ConversationDto getConversationByMeAndSecondUserName(String currentUserId, String secondUserId) {
        User secondUser = userService.getUserById(secondUserId);
        Conversation conversation = conversationRepository
                .getConversationByMeAndSecondId(currentUserId, secondUserId)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.Conversation.ERR_NOT_FOUND,
                            new String[]{"you and " + secondUser.getUsername()});
                });

        return conversationMapper.toConversationDto(conversation);
    }

    @Override
    public ConversationDto creatNewConversation(String firstUserId, String secondUserId) {
        User firstUser = userService.getUserById(firstUserId);
        User secondUser = userService.getUserById(secondUserId);

        Conversation conversation = new Conversation(firstUser, secondUser);

        return conversationMapper.toConversationDto(conversationRepository.save(conversation));
    }

    @Override
    public PaginationResponseDto<ConversationDto>
    getConversationsOfMe(String userId, PaginationFullRequestDto paginationFullRequestDto) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.Conversation);

        Page<Conversation> conversationPage = conversationRepository.getConversationsByUserId(userId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationFullRequestDto,
                SortByDataConstant.Conversation, conversationPage);

        List<Conversation> conversations = conversationPage.toList();

        return new PaginationResponseDto<>(pagingMeta, conversationMapper.toConversationDto(conversations));
    }
}
