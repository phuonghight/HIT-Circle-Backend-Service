package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class MessageController {

    private final MessageService messageService;

    @Tag(name = "message-controller")
    @Operation(summary = "API create new message")
    @GetMapping(value = UrlConstant.Message.SEND_MESSAGE_TO_OTHER)
    @PreAuthorize("hasRole('ROLE_ADMIN')") // tạm thời k cho send mess bằng api http
    public ResponseEntity<?> sendMessageToOtherById(@Valid @RequestBody MessageRequestDto messageRequestDto,
                                                    @Parameter(name = "user", hidden = true)
                                                    @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(messageService.sendMessageToOtherById(user.getId(), messageRequestDto));
    }

    @Tag(name = "message-controller")
    @Operation(summary = "API get message by conversation id")
    @GetMapping(value = UrlConstant.Message.GET_MESSAGES_BY_OTHER_BY_ID)
    public ResponseEntity<?>
    getMessagesByOtherById(@Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                       @Parameter(name = "user", hidden = true)
                       @CurrentUser UserPrincipal user,
                       @PathVariable String otherId) {
        return VsResponseUtil.success(messageService
                .getMessagesBySenderIdAndReceiverId(paginationFullRequestDto, user.getId(), otherId));
    }
}
