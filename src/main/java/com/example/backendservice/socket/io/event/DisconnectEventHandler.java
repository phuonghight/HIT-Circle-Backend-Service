package com.example.backendservice.socket.io.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.example.backendservice.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DisconnectEventHandler {

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        for (String room : client.getAllRooms()) {
            client.leaveRoom(room);
        }
        log.info("Client user with id {} disconnected", client.get(CommonConstant.Key.USER_ID).toString());
    }

}