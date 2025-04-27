package Gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final int user;

    public DashboardFrame(int user) {
        this.user = user;

        setTitle("BANK MANAGEMENT SYSTEM ");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("====  Welcome to SBI ATM  ====", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground( Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 15, 15));
        buttonPanel.setBackground( Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        // Load icons
        ImageIcon createIcon = new ImageIcon("icon/create.png");
        ImageIcon depositIcon = new ImageIcon("icon/deposit.png");
        ImageIcon withdrawIcon = new ImageIcon("icon/withdraw.png");
        ImageIcon transferIcon = new ImageIcon("icon/transfermoney.png");
        ImageIcon balanceIcon = new ImageIcon("icon/checkbalance.png");
        ImageIcon historyIcon = new ImageIcon("icon/transaction.png");
        ImageIcon logoutIcon = new ImageIcon("icon/logout.png");

        // Buttons
        JButton createAccountBtn = new JButton("CREATE ACCOUNT", createIcon);
        JButton depositBtn = new JButton("DEPOSIT MONEY", depositIcon);
        JButton withdrawBtn = new JButton("WITHDRAW MONEY", withdrawIcon);
        JButton transferBtn = new JButton("TRANSFER MONEY", transferIcon);
        JButton checkBalanceBtn = new JButton("CHECK BALANCE", balanceIcon);
        JButton historyBtn = new JButton("TRANSACTION HISTORY", historyIcon);
        JButton logoutBtn = new JButton("LOGOUT", logoutIcon);

        // Button styling
        styleButton(createAccountBtn, new Color(52, 152, 219));   // Blue
        styleButton(depositBtn, new Color(46, 204, 113));         // Green
        styleButton(withdrawBtn, new Color(230, 126, 34));        // Orange
        styleButton(transferBtn, new Color(155, 89, 182));        // Purple
        styleButton(checkBalanceBtn, new Color(241, 196, 15));    // Yellow
        styleButton(historyBtn, new Color(127, 140, 141));        // Gray
        styleButton(logoutBtn, new Color(231, 76, 60));           // Red

        // Add buttons to panel
        buttonPanel.add(createAccountBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(transferBtn);
        buttonPanel.add(checkBalanceBtn);
        buttonPanel.add(historyBtn);
        buttonPanel.add(logoutBtn);

        // Add panels to frame
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Actions
        createAccountBtn.addActionListener(e -> new CreateAccountFrame(user));
        depositBtn.addActionListener(e -> new DepositFrame(user));
        withdrawBtn.addActionListener(e -> new WithdrawFrame(user));
        transferBtn.addActionListener(e -> new TransferFrame(user));
        checkBalanceBtn.addActionListener(e -> new CheckBalanceFrame(user));
        historyBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Account ID:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int accountId = Integer.parseInt(input.trim());
                    new TransactionHistoryFrame(accountId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Account ID!");
                }
            }
        });
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
    }
}
