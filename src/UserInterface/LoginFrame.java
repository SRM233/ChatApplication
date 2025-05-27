package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginFrame extends JFrame {
    private JTextField usernameField;

    public LoginFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username label
        JLabel label = new JLabel("Enter your username:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field with fixed width
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(200, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this::handleLogin);

        // Add components with spacing
        mainPanel.add(label);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loginButton);

        add(mainPanel);
        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {

            try {
                //Create a socket connection to the server
                Socket socket = new Socket(Constant.IP_ADDRESS, Constant.PORT);

                //Create a DataOutputStream to send data to the server
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                //Send the message type
                dos.writeInt(1);

                //send the username to the server
                dos.writeUTF(username);
                

                new ChatFrame(username, socket);

                dispose();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "Username cannot be empty!");
        }
    }

}