package UserInterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChatFrame extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private DefaultListModel<String> userListModel;

    public ChatFrame(String username) {
        initializeUI(username);
    }

    private void initializeUI(String username) {
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
        userListModel.addElement(username);  // Add current user
        populateSampleUsers();  // Add sample data
        
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

    private void populateSampleUsers() {
        // Replace with actual user list from server in real implementation
        userListModel.addElement("User1");
        userListModel.addElement("User2");
        userListModel.addElement("User3");
    }

    private void handleSendAction(ActionEvent e) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("[You]: " + message + "\n");
            messageField.setText("");
        }
    }
}