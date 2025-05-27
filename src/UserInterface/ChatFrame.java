package UserInterface;

import javax.swing.*;

import Client.ClientThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatFrame extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private DefaultListModel<String> userListModel;
    private Socket socket;
    private String username;

    public ChatFrame(String username, Socket socket) {
        this.username = username;
        initializeUI();

        this.socket = socket;
        new ClientThread(socket, this).start();
    }

    private void initializeUI() {
        setTitle("Chat Room - " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Chat messages area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        mainPanel.add(chatScrollPane, BorderLayout.CENTER);

        // Online users list
        userListModel = new DefaultListModel<>();

        JList<String> userList = new JList<>(userListModel);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setPreferredSize(new Dimension(150, 0));
        mainPanel.add(userListScrollPane, BorderLayout.EAST);

        // Message input area
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(this::handleSendAction);
        messageField.addActionListener(this::handleSendAction);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void handleSendAction(ActionEvent e) {
        String message = messageField.getText().trim();
        // if (!message.isEmpty()) {
        // chatArea.append("[You]: " + message + "\n");
        // messageField.setText("");
        // }
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(2);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy--MM--dd HH:mm:ss EEE a");
            String dateTime = formatter.format(now);

            StringBuilder sb = new StringBuilder();
            String msg = sb.append(username).append(" ").append(dateTime).append("\r\n").append(message)
                    .toString();

            dos.writeUTF(msg);
            dos.flush();
            messageField.setText("");

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void updateChatFramdOnlineUsersList(String[] onlineUsers) {

        for (String username : onlineUsers) {
            if (!username.isEmpty()) {
                userListModel.addElement(username);
            }
        }

    }

    public void updateMsgToAll(String msg) {
        chatArea.append(msg + "\r\n");
    }
}