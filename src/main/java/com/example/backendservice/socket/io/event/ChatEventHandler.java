package com.example.backendservice.socket.io.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;
import com.example.backendservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatEventHandler {

    private final SocketIOServer server;

    private final MessageService messageService;

    @OnEvent(CommonConstant.Event.CLIENT_SEND_MESSAGE)
    public void handlerEventSendMessage(SocketIOClient client, MessageRequestDto message) {
        log.info("User {} send message: {}", message.getSenderId(), message.getMessage());

        MessageResponseDto messageResponseDto = messageService
                .sendMessageToOtherById(client.get(CommonConstant.Key.USER_ID), message);

        server.getRoomOperations(message.getSenderId())
                .sendEvent(CommonConstant.Event.SERVER_SEND_MESSAGE, messageResponseDto);
        server.getRoomOperations(message.getReceiverId())
                .sendEvent(CommonConstant.Event.SERVER_SEND_MESSAGE, messageResponseDto);
    }

}
