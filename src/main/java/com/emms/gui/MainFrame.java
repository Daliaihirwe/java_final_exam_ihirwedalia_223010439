package com.emms.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Energy Monitoring Management System (EMMS)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add the Plan management panel
        tabbedPane.addTab("Plans", new PlanPanel());
        tabbedPane.addTab("Customers", new CustomerPanel());
        tabbedPane.addTab("Meters", new MeterPanel());
        tabbedPane.addTab("Readings", new ReadingPanel());




        add(tabbedPane, BorderLayout.CENTER);
    }
}
