package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static final Map<Socket, String> OnlineUserNames = new HashMap<>();

    public static void main(String[] args) throws Exception {

        System.out.println("Server is running...");
        // Create a server socket with the specified port.
        ServerSocket serverSocket = new ServerSocket(Constant.PORT);

        // server socket accepts client socket connections then create a new Thread to handle the client.
        while (true) {
            Socket socket = serverSocket.accept();

            new ServerThread(socket).start();
        }

    }
}
