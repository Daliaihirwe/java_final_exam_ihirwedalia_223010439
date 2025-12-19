package com.emms;

import com.emms.gui.MainFrame;
import javax.swing.*;

public class EmmsApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
