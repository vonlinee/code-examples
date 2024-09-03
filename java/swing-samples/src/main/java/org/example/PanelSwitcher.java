package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSwitcher extends JFrame {

    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel panel1, panel2, panel3;

    public PanelSwitcher() {
        setTitle("Panel Switcher");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建CardLayout  
        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);

        // 创建面板  
        panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.add(new JLabel("Panel 1"));

        panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        panel2.add(new JLabel("Panel 2"));

        panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);
        panel3.add(new JLabel("Panel 3"));

        // 添加面板到CardLayout  
        contentPane.add(panel1, "Panel1");
        contentPane.add(panel2, "Panel2");
        contentPane.add(panel3, "Panel3");

        // 创建切换按钮  
        JButton btnPanel1 = new JButton("Panel 1");
        JButton btnPanel2 = new JButton("Panel 2");
        JButton btnPanel3 = new JButton("Panel 3");

        // 添加按钮到主框架  
        JPanel buttonPane = new JPanel();
        buttonPane.add(btnPanel1);
        buttonPane.add(btnPanel2);
        buttonPane.add(btnPanel3);

        // 布局设置  
        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);

        // 编写事件处理程序  
        btnPanel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Panel1");
            }
        });

        btnPanel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Panel2");
            }
        });

        btnPanel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Panel3");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new PanelSwitcher();
    }
}