package org.example.workx;

import javax.swing.*;
import java.awt.*;

public class CenteredLabeledListView {
    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("Centered Labeled ListView Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

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
        list.setVisibleRowCount(-1); // 自适应高度

        // 将列表放入滚动面板
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 150)); // 设置初始宽度
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 使用 GridBagConstraints 设置布局
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // 水平方向撑满父容器

        // 添加标题
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0; // 高度不变
        frame.add(titleLabel, gbc);

        // 添加列表
        gbc.gridy = 1;
        gbc.weighty = 1.0; // 允许列表自适应高度
        frame.add(scrollPane, gbc);

        // 设置窗口大小并显示
        frame.setSize(300, 250);
        frame.setVisible(true);
    }
}