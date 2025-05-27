package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

public class ServerThread extends Thread {
    private Socket socket;
    private DataInputStream dis;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());

            while (true) {
                int type = dis.readInt();

                switch (type) {
                    case 1:
                        // Read the username from the client
                        String name = dis.readUTF();
                        Server.OnlineUserNames.put(socket, name);
                        updateOnlineUserNamesList();
                        break;

                    case 2:

                        // Read the message from the client
                        String message = dis.readUTF();
                        updateMsgToAllUsers(message);

                        break;

                    default:
                        break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Client disconnected: " + socket.getInetAddress());
            Server.OnlineUserNames.remove(socket);
            updateOnlineUserNamesList();
        }
    }

    private void updateMsgToAllUsers(String message) {
        System.out.println(message);
        for (Socket socket : Server.OnlineUserNames.keySet()) {
            DataOutputStream dos;
            try {
                dos = new DataOutputStream(socket.getOutputStream());

                dos.writeInt(2);

                dos.writeUTF(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private void updateOnlineUserNamesList() {
        Collection<String> onLineUsers = Server.OnlineUserNames.values();

        for (Socket socket : Server.OnlineUserNames.keySet()) {

            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                dos.writeInt(1);

                dos.writeInt(onLineUsers.size());

                for (String userName : onLineUsers) {
                    dos.writeUTF(userName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
