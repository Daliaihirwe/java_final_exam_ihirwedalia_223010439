package com.emms.gui;

import com.emms.dao.CustomerDao;
import com.emms.dao.PlanDao;
import com.emms.dao.impl.CustomerDaoImpl;
import com.emms.dao.impl.PlanDaoImpl;
import com.emms.model.Customer;
import com.emms.model.Plan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class CustomerPanel extends JPanel {

    private final CustomerDao customerDao = new CustomerDaoImpl();
    private final PlanDao planDao = new PlanDaoImpl();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public CustomerPanel() {
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"ID", "Name", "Address", "Phone", "Email", "Plan ID"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Customer");
        JButton updateButton = new JButton("Update Customer");
        JButton deleteButton = new JButton("Delete Customer");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();

        addButton.addActionListener(e -> addCustomer());
        updateButton.addActionListener(e -> updateCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerDao.findAll();
        for (Customer customer : customers) {
            Vector<Object> row = new Vector<>();
            row.add(customer.getCustomerId());
            row.add(customer.getName());
            row.add(customer.getAddress());
            row.add(customer.getPhone());
            row.add(customer.getEmail());
            row.add(customer.getPlanId());
            tableModel.addRow(row);
        }
    }

    private void addCustomer() {
        CustomerFormPanel formPanel = new CustomerFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, formPanel, "Add New Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Customer customer = formPanel.getCustomer();
            customerDao.add(customer);
            refreshTable();
        }
    }

    private void updateCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        Customer existingCustomer = customerDao.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerFormPanel formPanel = new CustomerFormPanel(existingCustomer);
        int result = JOptionPane.showConfirmDialog(this, formPanel, "Update Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Customer updatedCustomer = formPanel.getCustomer();
            updatedCustomer.setCustomerId(existingCustomer.getCustomerId());
            customerDao.update(updatedCustomer);
            refreshTable();
        }
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            customerDao.delete(customerId);
            refreshTable();
        }
    }

    private class CustomerFormPanel extends JPanel {
        private final JTextField nameField;
        private final JTextField addressField;
        private final JTextField phoneField;
        private final JTextField emailField;
        private final JComboBox<PlanItem> planComboBox;

        public CustomerFormPanel(Customer customer) {
            setLayout(new GridLayout(0, 2, 5, 5));

            nameField = new JTextField(customer != null ? customer.getName() : "");
            addressField = new JTextField(customer != null ? customer.getAddress() : "");
            phoneField = new JTextField(customer != null ? customer.getPhone() : "");
            emailField = new JTextField(customer != null ? customer.getEmail() : "");

            List<Plan> plans = planDao.findAll();
            Vector<PlanItem> planItems = plans.stream()
                    .map(p -> new PlanItem(p.getPlanId(), p.getName()))
                    .collect(Collectors.toCollection(Vector::new));
            planComboBox = new JComboBox<>(planItems);

            if (customer != null) {
                for (int i = 0; i < planItems.size(); i++) {
                    if (planItems.get(i).getId() == customer.getPlanId()) {
                        planComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }

            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Address:"));
            add(addressField);
            add(new JLabel("Phone:"));
            add(phoneField);
            add(new JLabel("Email:"));
            add(emailField);
            add(new JLabel("Plan:"));
            add(planComboBox);
        }

        public Customer getCustomer() {
            Customer customer = new Customer();
            customer.setName(nameField.getText());
            customer.setAddress(addressField.getText());
            customer.setPhone(phoneField.getText());
            customer.setEmail(emailField.getText());
            PlanItem selectedPlan = (PlanItem) planComboBox.getSelectedItem();
            if (selectedPlan != null) {
                customer.setPlanId(selectedPlan.getId());
            }
            return customer;
        }
    }

    private static class PlanItem {
        private final int id;
        private final String name;

        public PlanItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
