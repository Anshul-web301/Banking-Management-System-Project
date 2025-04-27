package Gui;

import Service.AccountService;

import javax.swing.*;
import java.awt.*;

public class WithdrawFrame extends JFrame {
    private final JTextField accountIdField;
    private final JTextField amountField;
    private final AccountService accountService = new AccountService();
    private final int user;

    public WithdrawFrame(int user) {
        this.user = user;

        setTitle("Withdraw Money");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground( Color.BLACK);

        JLabel accountLabel = createLabel("Account ID:");
        accountIdField = createTextField();

        JLabel amountLabel = createLabel("Amount to Withdraw:");
        amountField = createTextField();

        JButton withdrawBtn = new JButton("Withdraw");
        JButton backBtn = new JButton("Back");
        styleButton(withdrawBtn, new Color(231, 76, 60)); // Red
        styleButton(backBtn, new Color(127, 140, 141));   // Gray

        withdrawBtn.addActionListener(e -> withdrawMoney());
        backBtn.addActionListener(e -> dispose());

        panel.add(accountLabel);
        panel.add(accountIdField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(withdrawBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);
    }

    private void withdrawMoney() {
        try {
            int accountId = Integer.parseInt(accountIdField.getText());
            double amount = Double.parseDouble(amountField.getText());

            boolean success = accountService.withdraw(accountId, amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Withdrawal failed. Insufficient balance or invalid account.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.BOLD, 14));
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return field;
    }

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }
}
