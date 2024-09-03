package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * 测试窗口
 */
public class TestFrame extends JFrame {

    public TestFrame(String title) throws HeadlessException {
        super(title);
        setSize(600, 400);
        setLocationRelativeTo(null); // 这将使 JFrame 在屏幕上居中
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showFrame() {
        setVisible(true);
    }

    public static void show(JComponent root) {
        TestFrame frame = new TestFrame("Test");
        frame.add(root);
        frame.setVisible(true);
    }
}
