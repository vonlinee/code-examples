package org.example.layout;

import javax.swing.*;
import java.awt.*;

public class GridLayoutExample1 {
    public static void main(String[] args) {  
        JFrame frame = new JFrame("GridLayout Example");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
          
        GridLayout gridLayout = new GridLayout(1, 8);
        frame.setLayout(gridLayout);  
          
        for (int i = 1; i <= 6; i++) {  
            JButton button = new JButton("Button " + i);

            frame.add(button, new GridBagConstraints());

            frame.add(button);
        }  
          
        frame.setSize(400, 300);  
        frame.setVisible(true);  
    }  
}