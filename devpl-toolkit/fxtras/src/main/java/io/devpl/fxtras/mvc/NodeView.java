package io.devpl.fxtras.mvc;

import javafx.scene.Parent;

/**
 * 基于Java代码的视图控制器
 */
public abstract class NodeView extends ViewBase {

    /**
     * 创建根节点
     * @return 根节点
     */
    abstract Parent createRoot();
}
