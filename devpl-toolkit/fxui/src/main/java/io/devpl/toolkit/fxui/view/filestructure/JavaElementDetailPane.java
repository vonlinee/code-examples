package io.devpl.toolkit.fxui.view.filestructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * Java元素详情面板
 * Center 部分随元素不同而切换
 */
public class JavaElementDetailPane extends BorderPane {

    public JavaElementDetailPane() {
        setTop(initTop());
    }

    private Node initTop() {
        ToolBar topToolBar = new ToolBar();
        topToolBar.getItems().add(new Button("Button 1"));
        topToolBar.getItems().add(new Button());
        topToolBar.getItems().add(new Button());
        return topToolBar;
    }

    /**
     * 更新详情信息
     */
    public void updateDetailInfo(JavaElementItem javaElementItem) {

    }
}
