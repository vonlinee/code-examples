package org.example;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static JPanel newPanel(Color bgColor) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(bgColor);
        return jPanel;
    }
}
