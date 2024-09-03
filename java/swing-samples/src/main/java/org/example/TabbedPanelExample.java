package org.example;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelExample {

    public static void main(String[] args) {

        JTabbedPane pane = new JTabbedPane();

        pane.addTab("A", Utils.newPanel(Color.RED));

        JPanel panel2 = Utils.newPanel(Color.GREEN);

        pane.addTab("B", panel2);
        pane.addTab("C", Utils.newPanel(Color.ORANGE));

        panel2.setLayout(new GridBagLayout());
        panel2.add(new JButton("111"));

        TestFrame.show(pane);
    }
}
