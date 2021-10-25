package server;

import com.sun.net.httpserver.HttpServer;
import handlers.CreatePuzzleHandler;
import handlers.FileHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/create_puzzle", new CreatePuzzleHandler());

        server.createContext("/", new FileHandler());


        System.out.println("Starting server");
        server.start();

        System.out.println("Server started");
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
