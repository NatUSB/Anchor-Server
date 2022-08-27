package com.anchorclient.server;

import com.anchorclient.server.util.Logger;

public class Launch {

    public static final Server server = new Server(27010);

    public static void main(String[] args) {
        Logger.log("Starting Anchor Client Communication Server...");
        server.start();
        server.startup();
    }

}
