package com.anchorclient.server.connection.packet;

import com.anchorclient.server.connection.Connection;
import org.java_websocket.WebSocket;

public abstract class Packet {

    public abstract void process(Connection conn, WebSocket socket);

}
