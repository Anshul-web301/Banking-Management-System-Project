package Gui;

import Modal.User;
import Service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel usernameErrorLabel;
    private final JLabel passwordErrorLabel;
    private final UserService userService = new UserService();
    private boolean isPasswordVisible = false;

    public LoginFrame() {
        setTitle("Banking Management System - Login");
        setSize(400, 330);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        wrapperPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        usernameField = new JTextField("Name...");
        usernameField.setForeground(Color.GRAY);
        usernameField.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (usernameField.getText().equals("Name...")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                    usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (usernameField.getText().trim().isEmpty()) {
                    usernameField.setText("Name...");
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
                }
            }
        });

        usernameErrorLabel = new JLabel(" ");
        usernameErrorLabel.setForeground(Color.RED);
        usernameErrorLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        passwordField = new JPasswordField();
        passwordField.setText("Password..");
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password..")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText("Password..");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
                }
            }
        });

        passwordErrorLabel = new JLabel(" ");
        passwordErrorLabel.setForeground(Color.RED);
        passwordErrorLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(new Color(44, 62, 80));
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JButton togglePasswordBtn = new JButton("👁");
        togglePasswordBtn.setPreferredSize(new Dimension(43, 21));
        togglePasswordBtn.addActionListener(this::togglePasswordVisibility);
        passwordPanel.add(togglePasswordBtn, BorderLayout.EAST);

        JLabel forgotLabel = new JLabel("<HTML><U>Forgot Password?</U></HTML>");
        forgotLabel.setForeground(Color.WHITE);
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setFont(new Font("Arial", Font.BOLD, 12));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleForgotPassword();
            }
        });

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Sign up");

        styleButton(loginBtn, new Color(231, 76, 60));
        styleButton(signupBtn, new Color(52, 152, 219));

        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });

        // Add components to GridBagLayout
        mainPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        mainPanel.add(usernameErrorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        mainPanel.add(passwordErrorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        mainPanel.add(forgotLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(loginBtn, gbc);
        gbc.gridx = 1;
        mainPanel.add(signupBtn, gbc);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        add(wrapperPanel);
        setVisible(true);
    }

    private void togglePasswordVisibility(ActionEvent e) {
        isPasswordVisible = !isPasswordVisible;
        if (String.valueOf(passwordField.getPassword()).equals("Enter your Password")) {
            passwordField.setEchoChar((char) 0); // Keep visible for placeholder
        } else {
            passwordField.setEchoChar(isPasswordVisible ? (char) 0 : '•');
        }
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        usernameErrorLabel.setText(" ");
        passwordErrorLabel.setText(" ");

        if (username.isEmpty() || username.equals("Enter your Name") ||
                password.isEmpty() || password.equals("Enter your Password")) {
            if (username.isEmpty() || username.equals("Enter your Name")) {
                usernameErrorLabel.setText("Username can't be Empty.");
            }
            if (password.isEmpty() || password.equals("Enter your Password")) {
                passwordErrorLabel.setText("Password can't be Empty.");
            }
            return;
        }

        User user = userService.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            new DashboardFrame(user.getUserId());
        } else {
            if (!userService.doesUserExist(username)) {
                usernameErrorLabel.setText(" Wrong Username");
            } else {
                passwordErrorLabel.setText(" Wrong Password");
            }
        }
    }

    private void handleForgotPassword() {
        String username = JOptionPane.showInputDialog(this, "Enter Your Username:");

        if (username != null && !username.trim().isEmpty()) {
            if (!userService.doesUserExist(username)) {
                JOptionPane.showMessageDialog(this, "❌ No such Username found.", "User Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newPassword = JOptionPane.showInputDialog(this, "Enter new Password:");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                boolean updated = userService.updatePassword(username, newPassword);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "✅ Password updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to update password. Please try again.");
                }
            }
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
