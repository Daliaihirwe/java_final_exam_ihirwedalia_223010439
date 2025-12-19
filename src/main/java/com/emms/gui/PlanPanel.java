package com.emms.gui;

import com.emms.dao.PlanDao;
import com.emms.dao.impl.PlanDaoImpl;
import com.emms.model.Plan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

public class PlanPanel extends JPanel {

    private final PlanDao planDao = new PlanDaoImpl();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public PlanPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Rate/kWh", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Plan");
        JButton updateButton = new JButton("Update Plan");
        JButton deleteButton = new JButton("Delete Plan");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();

        addButton.addActionListener(e -> addPlan());
        updateButton.addActionListener(e -> updatePlan());
        deleteButton.addActionListener(e -> deletePlan());
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear existing data
        List<Plan> plans = planDao.findAll();
        for (Plan plan : plans) {
            Vector<Object> row = new Vector<>();
            row.add(plan.getPlanId());
            row.add(plan.getName());
            row.add(plan.getRatePerKwh());
            row.add(plan.getDescription());
            tableModel.addRow(row);
        }
    }

    private void addPlan() {
        JTextField nameField = new JTextField();
        JTextField rateField = new JTextField();
        JTextField descriptionField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Rate per kWh:"));
        panel.add(rateField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Plan",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Plan plan = new Plan();
                plan.setName(nameField.getText());
                plan.setRatePerKwh(new BigDecimal(rateField.getText()));
                plan.setDescription(descriptionField.getText());
                planDao.add(plan);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid rate format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePlan() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a plan to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int planId = (int) tableModel.getValueAt(selectedRow, 0);
        Plan existingPlan = planDao.findById(planId).orElse(null);
        if (existingPlan == null) {
            JOptionPane.showMessageDialog(this, "Plan not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(existingPlan.getName());
        JTextField rateField = new JTextField(existingPlan.getRatePerKwh().toString());
        JTextField descriptionField = new JTextField(existingPlan.getDescription());
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Rate per kWh:"));
        panel.add(rateField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Plan",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                existingPlan.setName(nameField.getText());
                existingPlan.setRatePerKwh(new BigDecimal(rateField.getText()));
                existingPlan.setDescription(descriptionField.getText());
                planDao.update(existingPlan);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid rate format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletePlan() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a plan to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int planId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this plan?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            planDao.delete(planId);
            refreshTable();
        }
    }
}
