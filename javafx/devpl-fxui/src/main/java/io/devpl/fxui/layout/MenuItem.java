package io.devpl.fxui.layout;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class MenuItem extends TreeItem<String> {

    /**
     * 唯一ID
     */
    private String id;

    private Node content;

    public MenuItem(String title, Node content) {
        super(title);
        this.content = content;
    }

    public final Node getContent() {
        return content;
    }
}
