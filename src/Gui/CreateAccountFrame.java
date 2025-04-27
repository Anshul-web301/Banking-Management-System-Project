package Gui;

import Service.AccountService;

import javax.swing.*;
import java.awt.*;

public class CreateAccountFrame extends JFrame {
    private final JComboBox<String> accountTypeCombo;
    private final AccountService accountService = new AccountService();
    private final int user;

    public CreateAccountFrame(int user) {
        this.user = user;

        setTitle("Create Account");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel with background color
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBackground( Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel typeLabel = new JLabel("Account Type:");
        typeLabel.setForeground(Color.white);  // Set label text color
        mainPanel.add(typeLabel);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));


        String[] accountTypes = {"SAVINGS", "CURRENT"};
        accountTypeCombo = new JComboBox<>(accountTypes);
        mainPanel.add(accountTypeCombo);

        JButton createBtn = new JButton("Create");
        JButton backBtn = new JButton("Back");

        styleButton(createBtn, new Color(46, 204, 113)); // Green
        styleButton(backBtn, new Color(231, 76, 60));    // Red

        createBtn.addActionListener(e -> createAccount());
        backBtn.addActionListener(e -> dispose());

        mainPanel.add(createBtn);
        mainPanel.add(backBtn);

        add(mainPanel);
        setVisible(true);
    }

    private void createAccount() {
        String type = (String) accountTypeCombo.getSelectedItem();
        int accountId = accountService.createAccount(user, type);

        if (accountId > 0) {
            JOptionPane.showMessageDialog(this, "Account created successfully! Account ID: " + accountId);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account. Try again.");
        }
    }

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }
}
