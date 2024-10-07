package com.ademozalp.websocket.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Configuration
@NoArgsConstructor
@Slf4j
public class SocketUtils {

    public static final int SOCKET_SERVER_PORT = 9080;
    public static final String IP_IFY_URL = "https://api.ipify.org";
    public static final String SEND_NOTIFICATION = "send-notification";
    public static final String GET_NOTIFICATION = "get-notification";

//    @Bean
//    public String socketServerHostName() throws SocketException {
////        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
////            HttpGet request = new HttpGet(IP_IFY_URL);
////
////            HttpResponse response = httpClient.execute(request);
////
////            return EntityUtils.toString(response.getEntity());
////        } catch (Exception e) {
////            log.error("Error while get ip address: {}", e.getMessage());
////            throw new SocketException(Message.EXCEPTION_STANDING_UP_SOCKET_SERVER.getTr());
////        }
//        return null;
//    }
//
//    @Bean
//    public String privateSocketServerHostName() throws UnknownHostException {
//        return InetAddress.getLocalHost().getHostAddress();
//    }
}