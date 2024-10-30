package org.example.workx;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class EditableListExample {
    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("Editable JList Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 创建列表模型和列表
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Apple");
        listModel.addElement("Banana");
        listModel.addElement("Cherry");
        
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 添加选择监听器
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = list.getSelectedIndex();
                    if (index != -1) {
                        // 进入编辑模式
   
                    }
                }
            }
        });

        // 设置输入框作为单元格编辑器
        list.setCellRenderer(new EditableListCellRenderer());
        list.setFocusable(true);
        list.setVisibleRowCount(-1);

        // 将列表放入滚动面板
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 设置窗口大小并显示
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}

// 自定义单元格渲染器
class EditableListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // 使用 JTextField 作为单元格
        JTextField textField = new JTextField(value.toString());
        textField.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        textField.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
        textField.setEditable(true);
        return textField;
    }
}