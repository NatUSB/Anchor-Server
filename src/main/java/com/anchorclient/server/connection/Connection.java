package com.anchorclient.server.connection;
import lombok.Getter;
import org.java_websocket.WebSocket;

@Getter
public class Connection {

    private final String uuid, username;
    private final WebSocket socket;

    public Connection(String uuid, String username, WebSocket socket) {
        this.uuid = uuid;
        this.username = username;
        this.socket = socket;
    }

}