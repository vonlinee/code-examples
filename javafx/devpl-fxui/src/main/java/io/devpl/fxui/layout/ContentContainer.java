package io.devpl.fxui.layout;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * 内容区域
 */
public class ContentContainer extends ScrollPane {

    public ContentContainer() {
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    }

    public final void switchTo(Node node) {
        setContent(node);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }
}
