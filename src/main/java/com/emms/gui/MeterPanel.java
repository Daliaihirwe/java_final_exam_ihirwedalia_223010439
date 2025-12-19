package com.emms.gui;

import com.emms.dao.CustomerDao;
import com.emms.dao.MeterDao;
import com.emms.dao.impl.CustomerDaoImpl;
import com.emms.dao.impl.MeterDaoImpl;
import com.emms.model.Customer;
import com.emms.model.Meter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class MeterPanel extends JPanel {

    private final MeterDao meterDao = new MeterDaoImpl();
    private final CustomerDao customerDao = new CustomerDaoImpl();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public MeterPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Serial Number", "Installation Date", "Customer ID"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Meter");
        JButton updateButton = new JButton("Update Meter");
        JButton deleteButton = new JButton("Delete Meter");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();

        addButton.addActionListener(e -> addMeter());
        updateButton.addActionListener(e -> updateMeter());
        deleteButton.addActionListener(e -> deleteMeter());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Meter> meters = meterDao.findAll();
        for (Meter meter : meters) {
            Vector<Object> row = new Vector<>();
            row.add(meter.getMeterId());
            row.add(meter.getSerialNumber());
            row.add(meter.getInstallationDate());
            row.add(meter.getCustomerId());
            tableModel.addRow(row);
        }
    }

    private void addMeter() {
        MeterFormPanel formPanel = new MeterFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, formPanel, "Add New Meter",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Meter meter = formPanel.getMeter();
            meterDao.add(meter);
            refreshTable();
        }
    }

    private void updateMeter() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a meter to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int meterId = (int) tableModel.getValueAt(selectedRow, 0);
        Meter existingMeter = meterDao.findById(meterId).orElse(null);
        if (existingMeter == null) {
            JOptionPane.showMessageDialog(this, "Meter not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MeterFormPanel formPanel = new MeterFormPanel(existingMeter);
        int result = JOptionPane.showConfirmDialog(this, formPanel, "Update Meter",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Meter updatedMeter = formPanel.getMeter();
            updatedMeter.setMeterId(existingMeter.getMeterId());
            meterDao.update(updatedMeter);
            refreshTable();
        }
    }

    private void deleteMeter() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a meter to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int meterId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this meter?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            meterDao.delete(meterId);
            refreshTable();
        }
    }

    private class MeterFormPanel extends JPanel {
        private final JTextField serialNumberField;
        private final JComboBox<CustomerItem> customerComboBox;

        public MeterFormPanel(Meter meter) {
            setLayout(new GridLayout(0, 2, 5, 5));

            serialNumberField = new JTextField(meter != null ? meter.getSerialNumber() : "");

            List<Customer> customers = customerDao.findAll();
            Vector<CustomerItem> customerItems = customers.stream()
                    .map(c -> new CustomerItem(c.getCustomerId(), c.getName()))
                    .collect(Collectors.toCollection(Vector::new));
            customerComboBox = new JComboBox<>(customerItems);

            if (meter != null) {
                for (int i = 0; i < customerItems.size(); i++) {
                    if (customerItems.get(i).getId() == meter.getCustomerId()) {
                        customerComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }

            add(new JLabel("Serial Number:"));
            add(serialNumberField);
            add(new JLabel("Customer:"));
            add(customerComboBox);
        }

        public Meter getMeter() {
            Meter meter = new Meter();
            meter.setSerialNumber(serialNumberField.getText());
            meter.setInstallationDate(new Timestamp(new Date().getTime())); // Set current time on add/update
            CustomerItem selectedCustomer = (CustomerItem) customerComboBox.getSelectedItem();
            if (selectedCustomer != null) {
                meter.setCustomerId(selectedCustomer.getId());
            }
            return meter;
        }
    }

    private static class CustomerItem {
        private final int id;
        private final String name;

        public CustomerItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name + " (ID: " + id + ")";
        }
    }
}
