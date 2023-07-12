package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class ConversationController {

    private final ConversationService conversationService;

    @Tag(name = "conversation-controller")
    @Operation(summary = "API get conversation by id")
    @GetMapping(UrlConstant.Conversation.GET_CONVERSATION)
    public ResponseEntity<?> getConversationDtoById(@PathVariable String id) {
        return VsResponseUtil.success(conversationService.getConversationDtoById(id));
    }

    @Tag(name = "conversation-controller")
    @Operation(summary = "API get conversation of me and one guy")
    @GetMapping(UrlConstant.Conversation.GET_CONVERSATION_OF_ME_AND_ONE)
    public ResponseEntity<?> getConversationByMeAndSecondUserId(@PathVariable String userId,
                                         @Parameter(name = "user", hidden = true)
                                         @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(conversationService.getConversationByMeAndSecondUserId(user.getId(), userId));
    }

    @Tag(name = "conversation-controller")
    @Operation(summary = "API get conversations of me")
    @GetMapping(value = UrlConstant.Conversation.GET_CONVERSATIONS_OF_ME)
    public ResponseEntity<?> getConversationsOfMe(@Parameter(name = "user", hidden = true)
                                          @CurrentUser UserPrincipal user,
                                          @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(conversationService.getConversationsOfMe(user.getId(), paginationFullRequestDto));
    }

}
