package swing;

import javax.swing.*;
import java.awt.*;

public class SwingTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(400, 400);


        ImageIcon imageIcon = new ImageIcon();

        frame.add(new Button("Button"));
        frame.add(new Button("Button"));

        frame.setVisible(true);

    }
}
