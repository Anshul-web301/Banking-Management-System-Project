package Gui;

import Service.AccountService;

import javax.swing.*;
import java.awt.*;

public class TransferFrame extends JFrame {
    private final JTextField fromAccountField;
    private final JTextField toAccountField;
    private final JTextField amountField;
    private final AccountService accountService = new AccountService();
    private final int user;

    public TransferFrame(int user) {
        this.user = user;

        setTitle("Transfer Funds");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel with background color
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBackground( Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels and text fields
        JLabel fromLabel = new JLabel("From Account ID:");
        fromLabel.setForeground(Color.white);
        fromAccountField = new JTextField();
        fromLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel toLabel = new JLabel("To Account ID:");
        toLabel.setForeground(Color.WHITE);
        toAccountField = new JTextField();
        toLabel.setFont(new Font("Arial", Font.BOLD, 14));


        JLabel amountLabel = new JLabel("Amount to Transfer:");
        amountLabel.setForeground(Color.white);
        amountField = new JTextField();
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));


        // Buttons
        JButton transferBtn = new JButton("Transfer");
        JButton backBtn = new JButton("Back");

        styleButton(transferBtn, new Color(52, 152, 219));   // Blue
        styleButton(backBtn, new Color(231, 76, 60));        // Red

        // Add components to panel
        mainPanel.add(fromLabel);
        mainPanel.add(fromAccountField);
        mainPanel.add(toLabel);
        mainPanel.add(toAccountField);
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);
        mainPanel.add(transferBtn);
        mainPanel.add(backBtn);

        // Add to frame
        add(mainPanel);

        // Button actions
        transferBtn.addActionListener(e -> transferFunds());
        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void transferFunds() {
        try {
            int fromAccount = Integer.parseInt(fromAccountField.getText());
            int toAccount = Integer.parseInt(toAccountField.getText());
            double amount = Double.parseDouble(amountField.getText());

            boolean success = accountService.transferFunds(fromAccount, toAccount, amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Transfer successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Transfer failed. Check account IDs or balance.");
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
