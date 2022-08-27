package com.anchorclient.server.connection.packet.impl.server;

import com.anchorclient.server.connection.Connection;
import com.anchorclient.server.connection.packet.Packet;
import lombok.Getter;
import org.java_websocket.WebSocket;

@Getter
public class SPacketKick extends Packet {

    private final String reason;

    public SPacketKick(String reason) {
        this.reason = reason;
    }

    @Override
    public void process(Connection conn, WebSocket socket) {

    }

}
