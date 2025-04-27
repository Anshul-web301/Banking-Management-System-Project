package Gui;

import Modal.Transaction;
import Service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TransactionHistoryFrame extends JFrame {
    private JTable table;
    private final int accountId;

    public TransactionHistoryFrame(int accountId) {
        this.accountId = accountId;

        setTitle("Transaction History - Account ID: " + accountId);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel styling
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground( Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {
                "Transaction ID", "Account ID", "Transaction_Type", "Amount", "Transaction_Date", "Reference Account"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        styleTable();

        loadTransactionData(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground( Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton closeBtn = new JButton("Close");
        closeBtn.setForeground(Color.WHITE);

        closeBtn.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground( Color.WHITE);
        bottomPanel.add(closeBtn);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void loadTransactionData(DefaultTableModel model) {
        List<Transaction> transactions = TransactionService.getTransactionHistory(accountId);

        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found.");
        }

        for (Transaction t : transactions) {
            model.addRow(new Object[]{
                    t.gettransaction_id(),
                    t.getaccount_id(),
                    t.getTransactionType(),
                    t.getAmount(),
                    t.getTransactionDate(),
                    (t.getReferenceAccount() != null) ? t.getReferenceAccount() : ""
            });
        }

        table.setModel(model);
    }

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    private void styleTable() {
        table.setFont(new Font("Arial", Font.BOLD, 13));
        table.setRowHeight(25);
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.BLACK);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.BLACK);
        header.setForeground(Color.ORANGE);

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}
