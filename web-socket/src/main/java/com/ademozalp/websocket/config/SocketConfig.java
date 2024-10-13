package com.ademozalp.websocket.config;

import com.ademozalp.websocket.model.CmsNotificationDto;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static com.ademozalp.websocket.utils.SocketUtils.SOCKET_SERVER_HOST;
import static com.ademozalp.websocket.utils.SocketUtils.SOCKET_SERVER_PORT;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SocketConfig {

    private static final Gson gson;

    static {
        gson = new Gson();
    }

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();

        configuration.setHostname(SOCKET_SERVER_HOST);

        configuration.setPort(SOCKET_SERVER_PORT);

        configuration.setOrigin(null);

        com.corundumstudio.socketio.SocketConfig socketConfig = new com.corundumstudio.socketio.SocketConfig();

        socketConfig.setReuseAddress(true);

        configuration.setSocketConfig(socketConfig);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(CmsNotificationDto.class, new CmsNotificationDtoDeserializer());
        configuration.setJsonSupport(new JacksonJsonSupport(module));

        SocketIOServer socketIOServer = new SocketIOServer(configuration);

        socketIOServer.start();

        return socketIOServer;
    }

    public static class CmsNotificationDtoDeserializer extends JsonDeserializer<CmsNotificationDto> {
        @Override
        public CmsNotificationDto deserialize(JsonParser p, DeserializationContext ct) throws IOException {
            ObjectMapper mapper = (ObjectMapper) p.getCodec();

            JsonNode node = mapper.readTree(p);

            String jsonString = mapper.writeValueAsString(node);

            jsonString = jsonString.replace("\\\"", "\"");

            if (jsonString.startsWith("\"") && jsonString.endsWith("\"")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
            }

            return gson.fromJson(jsonString, CmsNotificationDto.class);
        }
    }
}
