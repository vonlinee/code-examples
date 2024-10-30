package org.example.java8.api;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class C01_String {

    public static void main(String[] args) {
        String s1 = "a" + "b" + "c";

        for (int i = 0; i < 100; i++) {
            s1 = s1 + "" + i;
        }
        System.out.println();

        TreeView<String> treeView = null;

        TreeItem<String> root = treeView.getRoot();

    }

}
