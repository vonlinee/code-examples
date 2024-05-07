package org.example;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Example {
  public static void main(String[] args) {
    JFrame frame = new JFrame("BoxLayout Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个面板，并使用BoxLayout分割成上、中、下三个区域
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    JButton button1 = new JButton("Button 1");
    JButton button2 = new JButton("Button 2");
    JButton button3 = new JButton("Button 3");
    panel.add(button1);
    panel.add(Box.createVerticalGlue());
    panel.add(button2);
    panel.add(Box.createVerticalGlue());
    panel.add(button3);

    // 设置需要拉伸的区域
    panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
    button2.setAlignmentX(JButton.CENTER_ALIGNMENT);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}