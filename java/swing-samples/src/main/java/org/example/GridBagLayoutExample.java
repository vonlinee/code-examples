package org.example;

import javax.swing.*;
import java.awt.*;  
  
public class GridBagLayoutExample {  
  
    public static void main(String[] args) {  
        // 创建一个 JFrame 实例  
        JFrame frame = new JFrame("GridBagLayout 示例");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        // 创建一个 JPanel，并设置其布局为 GridBagLayout  
        JPanel panel = new JPanel(new GridBagLayout());  
  
        // 创建一些组件  
        JLabel label1 = new JLabel("标签 1");  
        JLabel label2 = new JLabel("标签 2（更长）");  
        JButton button1 = new JButton("按钮 1");  
        JButton button2 = new JButton("按钮 2");  
  
        // 创建一个 GridBagConstraints 实例，用于设置组件的网格属性  
        GridBagConstraints gbc = new GridBagConstraints();  
  
        // 设置标签 1 的属性  
        gbc.gridx = 0; // 列索引  
        gbc.gridy = 0; // 行索引  
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充  
        panel.add(label1, gbc);  
  
        // 设置标签 2 的属性，注意我们没有重置 gbc，所以它会继续使用之前的设置  
        gbc.gridy = 1; // 仅改变行索引  
        panel.add(label2, gbc);  
  
        // 重置 gbc 以设置按钮 1 的属性  
        gbc.gridx = 1;  
        gbc.gridy = 0;  
        gbc.fill = GridBagConstraints.NONE; // 不填充  
        panel.add(button1, gbc);  
  
        // 设置按钮 2 的属性  
        gbc.gridy = 1;  
        gbc.gridwidth = 2; // 跨越两列  
        panel.add(button2, gbc);  
  
        // 将 JPanel 添加到 JFrame  
        frame.add(panel);  
  
        // 设置 JFrame 的大小和可见性  
        frame.setSize(300, 200);  
        frame.setVisible(true);  
    }  
}