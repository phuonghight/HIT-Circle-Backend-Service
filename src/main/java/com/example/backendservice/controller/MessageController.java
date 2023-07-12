package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.MessageService;
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
public class MessageController {

    private final MessageService messageService;

    @Tag(name = "message-controller")
    @Operation(summary = "API get message by conversation id")
    @GetMapping(value = UrlConstant.Message.GET_MESSAGES_BY_CONVERSATION)
    public ResponseEntity<?> getMessagesByConversationId(@Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                        @PathVariable String conversationId) {
        return VsResponseUtil.success(messageService
                .getMessagesByConversationId(paginationFullRequestDto, conversationId));
    }

}
