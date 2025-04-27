package Gui;

import Service.AccountService;

import javax.swing.*;
import java.awt.*;

public class DepositFrame extends JFrame {
    private final JTextField accountIdField;
    private final JTextField amountField;
    private final AccountService accountService = new AccountService();
    private final int user;

    public DepositFrame(int user) {
        this.user = user;

        setTitle("Deposit Money");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel setup
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.setBackground( Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels and input fields
        JLabel accountIdLabel = new JLabel("Account ID:");
        accountIdLabel.setForeground(Color.white);
        accountIdField = new JTextField();
        accountIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel amountLabel = new JLabel("Amount to Deposit:");
        amountLabel.setForeground(Color.white);
        amountField = new JTextField();
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Buttons
        JButton depositBtn = new JButton("Deposit");
        JButton backBtn = new JButton("Back");

        styleButton(depositBtn, new Color(46, 204, 113));  // Green
        styleButton(backBtn, new Color(231, 76, 60));      // Red

        // Add components to panel
        mainPanel.add(accountIdLabel);
        mainPanel.add(accountIdField);
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);
        mainPanel.add(depositBtn);
        mainPanel.add(backBtn);

        // Add panel to frame
        add(mainPanel);

        // Action listeners
        depositBtn.addActionListener(e -> depositMoney());
        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void depositMoney() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText());
            double amount = Double.parseDouble(amountField.getText());

            boolean success = accountService.deposit(accountId, amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Deposit successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Deposit failed. Check account details.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
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
