package org.example;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SideBar extends TreeView<String> {
    public SideBar() {
        setRoot(new TreeItem<>("Root"));
    }
}
