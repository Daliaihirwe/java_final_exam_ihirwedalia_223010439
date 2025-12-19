package com.emms.gui;

import com.emms.dao.MeterDao;
import com.emms.dao.ReadingDao;
import com.emms.dao.impl.MeterDaoImpl;
import com.emms.dao.impl.ReadingDaoImpl;
import com.emms.model.Meter;
import com.emms.model.Reading;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ReadingPanel extends JPanel {

    private final ReadingDao readingDao = new ReadingDaoImpl();
    private final MeterDao meterDao = new MeterDaoImpl();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ReadingPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Meter ID", "Timestamp", "kWh Consumed"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Reading");
        JButton deleteButton = new JButton("Delete Reading");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();

        addButton.addActionListener(e -> addReading());
        deleteButton.addActionListener(e -> deleteReading());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Reading> readings = readingDao.findAll();
        for (Reading reading : readings) {
            Vector<Object> row = new Vector<>();
            row.add(reading.getReadingId());
            row.add(reading.getMeterId());
            row.add(reading.getReadingTimestamp());
            row.add(reading.getKwhConsumed());
            tableModel.addRow(row);
        }
    }

    private void addReading() {
        ReadingFormPanel formPanel = new ReadingFormPanel();
        int result = JOptionPane.showConfirmDialog(this, formPanel, "Add New Reading",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Reading reading = formPanel.getReading();
                readingDao.add(reading);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid kWh format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteReading() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reading to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long readingId = (long) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this reading? This action cannot be undone.",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            readingDao.delete(readingId);
            refreshTable();
        }
    }

    private class ReadingFormPanel extends JPanel {
        private final JComboBox<MeterItem> meterComboBox;
        private final JTextField kwhField;

        public ReadingFormPanel() {
            setLayout(new GridLayout(0, 2, 5, 5));

            kwhField = new JTextField();

            List<Meter> meters = meterDao.findAll();
            Vector<MeterItem> meterItems = meters.stream()
                    .map(m -> new MeterItem(m.getMeterId(), m.getSerialNumber()))
                    .collect(Collectors.toCollection(Vector::new));
            meterComboBox = new JComboBox<>(meterItems);

            add(new JLabel("Meter:"));
            add(meterComboBox);
            add(new JLabel("kWh Consumed:"));
            add(kwhField);
        }

        public Reading getReading() {
            Reading reading = new Reading();
            reading.setKwhConsumed(new BigDecimal(kwhField.getText()));
            reading.setReadingTimestamp(new Timestamp(new Date().getTime()));
            MeterItem selectedMeter = (MeterItem) meterComboBox.getSelectedItem();
            if (selectedMeter != null) {
                reading.setMeterId(selectedMeter.getId());
            }
            return reading;
        }
    }

    private static class MeterItem {
        private final int id;
        private final String serial;

        public MeterItem(int id, String serial) {
            this.id = id;
            this.serial = serial;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "SN: " + serial + " (ID: " + id + ")";
        }
    }
}
