package org.example.layout;

import java.awt.GridLayout;
import javax.swing.JFrame;  
import javax.swing.JButton;  
  
public class GridLayoutExample {  
    public static void main(String[] args) {  
        JFrame frame = new JFrame("GridLayout Example");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
          
        GridLayout gridLayout = new GridLayout(3, 2);  
        frame.setLayout(gridLayout);  
          
        for (int i = 1; i <= 6; i++) {  
            JButton button = new JButton("Button " + i);  
            frame.add(button);  
        }  
          
        frame.setSize(400, 300);  
        frame.setVisible(true);  
    }  
}