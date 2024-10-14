package com.ademozalp.websocket.socket;

import com.ademozalp.websocket.model.CmsNotificationDto;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.ademozalp.websocket.utils.SocketUtils.GET_CMS_NOTIFICATION;
import static com.ademozalp.websocket.utils.SocketUtils.SEND_CMS_NOTIFICATION;

@Component
@Slf4j
public class SocketModule {

    public SocketModule(SocketIOServer socketIOServer) {
        socketIOServer.addConnectListener(onConnect());
        socketIOServer.addDisconnectListener(onDisconnect());
        socketIOServer.addEventListener(SEND_CMS_NOTIFICATION, CmsNotificationDto.class, onNotificationReceived());
    }

    private ConnectListener onConnect() {
        return client -> {
            Optional<String> rooms = Optional.ofNullable(client.getHandshakeData().getSingleUrlParam("room"));
            if (rooms.isPresent() && !rooms.get().isEmpty()) {
                String[] roomList = rooms.get().split(",");
                for (String room : roomList) {
                    client.joinRoom(room);
                }
                log.info("SocketId: {} connected -> room: {}", client.getSessionId(), roomList);
            } else {
                client.disconnect();
                log.info("Client has no room disconnected");
            }
        };
    }

    private DisconnectListener onDisconnect() {
        return client -> log.info("SocketId: {} disconnected", client.getSessionId());
    }

    private DataListener<CmsNotificationDto> onNotificationReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("{} -> {}", senderClient.getSessionId(), data);
            Set<UUID> sessionIdList = new HashSet<>();

            data.getRolesList()
                    .forEach(role -> senderClient.getNamespace().getRoomOperations(role).getClients()
                            .forEach(client -> {
                                if (!sessionIdList.contains(client.getSessionId()) && !client.getSessionId().equals(senderClient.getSessionId())) {
                                    client.sendEvent(GET_CMS_NOTIFICATION, data);
                                    ackSender.sendAckData(true);
                                    sessionIdList.add(client.getSessionId());
                                }
                            }));
        };
    }
}
