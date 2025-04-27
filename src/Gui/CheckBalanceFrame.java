package Gui;

import Service.AccountService;

import javax.swing.*;
import java.awt.*;

public class CheckBalanceFrame extends JFrame {
    private final JTextField accountIdField;
    private final JLabel balanceLabel;
    private final AccountService accountService = new AccountService();

    public CheckBalanceFrame(int user) {
        setTitle("Check Account Balance");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBackground( Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel accountIdLabel = new JLabel("Enter Account ID:");
        accountIdLabel.setForeground(Color.white);
        accountIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        accountIdField = new JTextField();
        accountIdField.setFont(new Font("Arial", Font.BOLD, 14));
        accountIdField.setBackground(new Color(236, 240, 241));
        accountIdField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton checkButton = new JButton("Check Balance");
        styleButton(checkButton, new Color(41, 128, 185)); // Blue

        balanceLabel = new JLabel("Balance: ", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        balanceLabel.setForeground(Color.WHITE);

        checkButton.addActionListener(e -> checkBalance());

        mainPanel.add(accountIdLabel);
        mainPanel.add(accountIdField);
        mainPanel.add(checkButton);
        mainPanel.add(balanceLabel);

        add(mainPanel);
        setVisible(true);
    }

    private void checkBalance() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText());
            double balance = accountService.checkBalance(accountId);
            if (balance >= 0) {
                balanceLabel.setText("Balance: $" + balance);
            } else {
                balanceLabel.setText("Error: Account not found or database error.");
            }
        } catch (NumberFormatException e) {
            balanceLabel.setText("Please enter a valid numeric Account ID.");
        }
    }

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }
}
