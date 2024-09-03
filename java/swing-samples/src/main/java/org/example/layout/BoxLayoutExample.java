package org.example.layout;

import org.example.TestFrame;
import org.example.Utils;

import javax.swing.*;
import java.awt.*;

public class BoxLayoutExample {

    public static void main(String[] args) {

        Box box = Box.createVerticalBox();


        box.add(Utils.newPanel(Color.RED));
        box.add(Utils.newPanel(Color.GREEN));
        box.add(Utils.newPanel(Color.YELLOW));

        TestFrame.show(box);
    }
}
