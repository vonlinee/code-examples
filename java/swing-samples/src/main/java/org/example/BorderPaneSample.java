package org.example;

import javax.swing.*;
import java.awt.*;

public class BorderPaneSample {

    public static void main(String[] args) {

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(400, 400));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel1.setBackground(Color.RED);
        panel2.setBackground(Color.GREEN);
        panel3.setBackground(Color.YELLOW);
        panel4.setBackground(Color.GREEN);
        panel5.setBackground(Color.ORANGE);

        jPanel.add(panel1, BorderLayout.NORTH);
        jPanel.add(panel2, BorderLayout.SOUTH);
        jPanel.add(panel3, BorderLayout.CENTER);
        jPanel.add(panel4, BorderLayout.EAST);
        jPanel.add(panel5, BorderLayout.WEST);

        panel3.add(new JButton("111"));

        TestFrame frame = new TestFrame("测试BorderLayout布局");
        frame.add(jPanel);
        frame.showFrame();
    }
}
