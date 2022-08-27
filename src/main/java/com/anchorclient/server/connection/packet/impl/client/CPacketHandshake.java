package com.anchorclient.server.connection.packet.impl.client;

import com.anchorclient.server.Launch;
import com.anchorclient.server.connection.Connection;
import com.anchorclient.server.connection.packet.Packet;

import com.anchorclient.server.connection.packet.impl.server.SPacketPlayerCount;
import com.anchorclient.server.util.Logger;
import org.java_websocket.WebSocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CPacketHandshake extends Packet {

    private final String uuid, name;

    public CPacketHandshake(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void process(Connection conn, WebSocket sock) {
        Launch.server.clients.add(new Connection(uuid, name, sock));
        Logger.log("Client Connected: " + uuid + " " + name);
        Launch.server.broadcastPacket(new SPacketPlayerCount());
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
