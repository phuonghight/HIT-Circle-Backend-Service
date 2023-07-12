package com.example.backendservice.event;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;
import com.example.backendservice.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatEventHandler {

    private final SocketIOServer server;

    @Autowired
    private MessageService messageService;

    public ChatEventHandler(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("client_join_conversation", String.class, onEventJoinRoom());
        server.addEventListener("client_send_message", MessageRequestDto.class, onEventSendMessage());
    }

    private ConnectListener onConnected() {
        return socketIOClient -> {
            log.info("A user connected with session id: {}", socketIOClient.getSessionId());
            String token = socketIOClient.getHandshakeData().getSingleUrlParam("accessToken");
            System.out.println(token);
        };
    }


    private DisconnectListener onDisconnected () {
        return socketIOClient -> log.info("A user disconnected with session id: {}",
                socketIOClient.getSessionId());
    }

    private DataListener<String> onEventJoinRoom() {
        return (client, conversationId, ackRequest) -> {
            log.info("User with session id {} join room: {}", client.getSessionId(), conversationId);
            client.joinRoom(conversationId);
        };
    }

    private DataListener<MessageRequestDto> onEventSendMessage() {
        return (client, messageRequestDto, ackRequest) -> {
            MessageResponseDto messageResponseDto = messageService.creatNewMessage(messageRequestDto);

            server.getRoomOperations(messageRequestDto.getConversationId())
                    .sendEvent("server_send_message", messageResponseDto);
            log.info("User with session id {} send message: {}", client.getSessionId(), messageRequestDto.getMessage());
        };
    }


}
