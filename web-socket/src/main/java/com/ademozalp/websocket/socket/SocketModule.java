package com.ademozalp.websocket.socket;

import com.ademozalp.websocket.model.Notification;
import com.ademozalp.websocket.service.NotificationService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ademozalp.websocket.utils.SocketUtils.GET_NOTIFICATION;
import static com.ademozalp.websocket.utils.SocketUtils.SEND_NOTIFICATION;


@Component
@Slf4j
public class SocketModule {

    private final NotificationService notificationService;

    public SocketModule(SocketIOServer socketIOServer, NotificationService notificationService) {
        this.notificationService = notificationService;
        socketIOServer.addConnectListener(onConnect());
        socketIOServer.addDisconnectListener(onDisconnect());
        socketIOServer.addEventListener(SEND_NOTIFICATION, Notification.class, onNotificationReceived());
    }

    private ConnectListener onConnect() {
        return client -> log.info("SocketId: {} connected", client.getSessionId());
    }

    private DisconnectListener onDisconnect() {
        return client -> log.info("SocketId: {} disconnected", client.getSessionId());
    }

    private DataListener<Notification> onNotificationReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("{} -> {}", senderClient.getSessionId(), data);
            Notification response = notificationService.save(data);
            senderClient.getNamespace().getAllClients().forEach(client -> {
                if (!client.getSessionId().equals(senderClient.getSessionId())) {
                    client.sendEvent(GET_NOTIFICATION, response);
                    ackSender.sendAckData(true);
                }
            });
        };
    }


}
