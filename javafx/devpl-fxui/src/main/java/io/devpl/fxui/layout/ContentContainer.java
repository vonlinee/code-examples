package io.devpl.fxui.layout;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * 内容区域
 */
public class ContentContainer extends ScrollPane {

    Node currentContent;

    Map<Integer, Node> childrenMap = new IdentityHashMap<>();

    public void switchTo(Node node) {
        setContent(node);
    }
}
