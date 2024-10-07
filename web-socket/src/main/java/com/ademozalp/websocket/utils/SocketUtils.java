package com.ademozalp.websocket.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketUtils {

    public static final String SOCKET_SERVER_HOST = "0.0.0.0";
    public static final int SOCKET_SERVER_PORT = 9080;
    public static final String SEND_NOTIFICATION = "send-notification";
    public static final String GET_NOTIFICATION = "get-notification";

}