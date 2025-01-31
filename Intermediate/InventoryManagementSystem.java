package Intermediate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementSystem extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameInput, quantityInput, priceInput;

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        nameInput = new JTextField(10);
        quantityInput = new JTextField(10);
        priceInput = new JTextField(10);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameInput);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityInput);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceInput);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void addItem() {
        try {
            String name = nameInput.getText();
            int quantity = Integer.parseInt(quantityInput.getText());
            double price = Double.parseDouble(priceInput.getText());

            tableModel.addRow(new Object[]{name, quantity, price});
            clearInputs();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid quantity and price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String name = nameInput.getText();
                int quantity = Integer.parseInt(quantityInput.getText());
                double price = Double.parseDouble(priceInput.getText());

                tableModel.setValueAt(name, selectedRow, 0);
                tableModel.setValueAt(quantity, selectedRow, 1);
                tableModel.setValueAt(price, selectedRow, 2);
                clearInputs();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid quantity and price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No item selected. Please select an item to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
            clearInputs();
        } else {
            JOptionPane.showMessageDialog(this, "No item selected. Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputs() {
        nameInput.setText("");
        quantityInput.setText("");
        priceInput.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryManagementSystem().setVisible(true);
            }
        });
    }
}