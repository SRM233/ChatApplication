package UserInterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
            SwingUtilities.invokeLater(() -> {
                new ChatFrame(username);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "Username cannot be empty!");
        }
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(LoginFrame::new);
    // }
}