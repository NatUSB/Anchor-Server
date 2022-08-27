package com.anchorclient.server;

import com.anchorclient.server.connection.Connection;
import com.anchorclient.server.connection.packet.Packet;
import com.anchorclient.server.connection.packet.PacketBuilder;
import com.anchorclient.server.connection.packet.impl.server.SPacketKick;
import com.anchorclient.server.connection.packet.impl.server.SPacketPlayerCount;
import com.anchorclient.server.util.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Server extends WebSocketServer {

    public List<Connection> clients = new ArrayList<>();

    public Server(int port) {
        super(new InetSocketAddress(port));
        new Thread(this::listenToCommands).start();
    }

    private void listenToCommands() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {
                String command = reader.readLine();

                if(command.equals("list")) {
                    if(!clients.isEmpty()) {
                        for(Connection c : clients) {
                            System.out.println(c.getUuid() + " | " + c.getUsername());
                        }

                        System.out.println("Listed " + clients.size() + " online users!");
                    } else {
                        System.out.println("No users are currently online!");
                    }
                } else if(command.startsWith("kick")) {
                    String[] args = command.split(" ", 3);
                    if(args.length == 3) {
                        String user = args[1];
                        String reason = args[2];

                        if(!user.isEmpty()) {
                            boolean foundUser = false;
                            for(Connection c : clients) {
                                if(c.getUsername().equals(user) || c.getUuid().equals(user)) {
                                    foundUser = true;
                                    c.getSocket().send(PacketBuilder.encode(new SPacketKick(reason)));
                                    System.out.println("Kicked " + user + " for reason: " + reason);
                                }
                            }

                            if(!foundUser) {
                                System.out.println("No user with that username/uuid found!");
                            }
                        } else {
                            System.out.println("No user inputted! ");
                        }
                    } else {
                        System.out.println("Invalid syntax! Ex: kick (user) (reason)");
                    }
                } else {
                    System.out.println("Command not found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startup() {
        Logger.log("Anchor Client Communications Server Started!");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        clients.stream().filter(cc -> cc.getSocket() == conn).forEach(cc -> {
            clients.remove(cc);
            Logger.log("Client Disconnected! " + cc.getUuid());
            broadcastPacket(new SPacketPlayerCount());
        });
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Logger.log("packet received " + message);
        Packet packet = PacketBuilder.build(message);
        packet.process(getClientBySocket(conn), conn);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }

    public Connection getClientBySocket(WebSocket socket) {
        return clients.stream().filter(c -> c.getSocket() == socket).findFirst().orElse(null);
    }

    public final void broadcastPacket(Packet p) {
        this.clients.forEach(cc -> cc.getSocket().send(PacketBuilder.encode(p)));
    }

}
