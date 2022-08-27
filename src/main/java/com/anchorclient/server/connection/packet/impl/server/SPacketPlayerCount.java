package com.anchorclient.server.connection.packet.impl.server;

import com.anchorclient.server.Launch;
import com.anchorclient.server.connection.Connection;
import com.anchorclient.server.connection.packet.Packet;

import lombok.Getter;
import org.java_websocket.WebSocket;

@Getter
public class SPacketPlayerCount extends Packet {

    private final int playerCount;

    public SPacketPlayerCount() {
        this.playerCount = Launch.server.clients.size();
    }

    @Override
    public void process(Connection conn, WebSocket socket) {

    }

}
