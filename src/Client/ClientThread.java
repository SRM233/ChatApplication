package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import UserInterface.ChatFrame;

import UserInterface.ChatFrame;

public class ClientThread extends Thread {

    private Socket socket;
    private ChatFrame cf;
    private DataInputStream dis;

    public ClientThread(Socket socket, ChatFrame cf) {
        this.socket = socket;
        this.cf = cf;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());

            while (true) {
                int type = dis.readInt();
                switch (type) {
                    case 1:
                        updateOnlineUsersList();
                        break;

                    case 2:
                        updateChatToAll();
                        break;

                    default:
                        break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void updateChatToAll() {
        try {
            String msg = dis.readUTF();

            cf.updateMsgToAll(msg);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void updateOnlineUsersList() {
        try {
            int users = dis.readInt();

            String[] onlineUsers = new String[users];

            for (int i = 0; i < users; i++) {
                onlineUsers[i] = dis.readUTF();
            }

            cf.updateChatFramdOnlineUsersList(onlineUsers);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
