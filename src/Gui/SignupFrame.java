package Gui;

import Service.UserService;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final UserService userService = new UserService();

    public SignupFrame() {
        setTitle("Banking Management System - Sign Up");
        setSize(400, 270);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main wrapper
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground( Color.BLACK);

        // Title at top
        JLabel titleLabel = new JLabel("SIGNUP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.white);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        wrapperPanel.add(titleLabel, BorderLayout.NORTH);

        // Main form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground( Color.BLACK);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel usernameLabel = new JLabel("New Username:-");
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField = new JTextField();


        JLabel passwordLabel = new JLabel("New Password:-");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField = new JPasswordField();

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        styleButton(registerBtn, new Color(46, 204, 113));   // Green
        styleButton(backBtn, new Color(243, 156, 18));       // Orange

        registerBtn.addActionListener(e -> registerUser());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        // Add form components
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(registerBtn);
        formPanel.add(backBtn);

        wrapperPanel.add(formPanel, BorderLayout.CENTER);
        add(wrapperPanel);

        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        boolean success = userService.register(username, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose();
            new LoginFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists or registration failed.");
        }
    }

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }
}
