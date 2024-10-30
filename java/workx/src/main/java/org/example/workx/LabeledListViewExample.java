package org.example.workx;

import javax.swing.*;
import java.awt.*;

public class LabeledListViewExample {
    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("Labeled ListView Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 创建标题标签
        JLabel titleLabel = new JLabel("Fruits List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // 创建列表模型和列表
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Apple");
        listModel.addElement("Banana");
        listModel.addElement("Cherry");
        listModel.addElement("Date");
        listModel.addElement("Elderberry");

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 将列表放入滚动面板
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 150));

        // 将标题和列表添加到窗口
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 设置窗口大小并显示
        frame.setSize(300, 250);
        frame.setVisible(true);
    }
}