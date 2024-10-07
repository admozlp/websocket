package com.ademozalp.websocket.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ademozalp.websocket.utils.SocketUtils.SOCKET_SERVER_HOST;
import static com.ademozalp.websocket.utils.SocketUtils.SOCKET_SERVER_PORT;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SocketConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();

        configuration.setHostname(SOCKET_SERVER_HOST);

        configuration.setPort(SOCKET_SERVER_PORT);

        configuration.setOrigin("http://192.168.68.116:8080");

        return new SocketIOServer(configuration);
    }
}
