package io.devpl.tookit.fxui.view.filestructure;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Java元素详情面板
 * Center 部分随元素不同而切换
 */
public class JavaElementDetailPane extends BorderPane {

    public JavaElementDetailPane() {
        setTop(initTop());
    }

    private Node initTop() {
        HBox hBox = new HBox();

        Label label = new Label();

        return hBox;
    }

    /**
     * 更新详情信息
     */
    public void updateDetailInfo(JavaElementItem javaElementItem) {

    }
}
