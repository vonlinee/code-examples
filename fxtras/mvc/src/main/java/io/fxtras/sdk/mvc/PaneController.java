package io.fxtras.sdk.mvc;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class PaneController extends StageController {

    // 必须用@FXML标注，不然无法注入
    @FXML
    private Pane root;

    /**
     * 更新Root节点
     * @param root root节点
     */
    public final void setRoot(Pane root) {
        if (root != null) {
            initOwner(this.root);
        }
    }

    protected void initialize() {
    }

    /**
     * @param pane 根容器
     * @see PaneController#setRoot(Pane)
     */
    private void initOwner(Pane pane) {
        // Scene#setRoot时触发
        addDisposableListener(pane.sceneProperty(), (o, ov, nv) -> {
            if (ov == null && nv != null) {
                super.setScene(nv);
                if (nv.getWindow() != null) return;
                // Stage#setScene时触发
                addDisposableListener(nv.windowProperty(), (o1, ov1, nv1) -> {
                    if (ov1 == null && nv1 != null) {
                        super.setStage((Stage) nv1);
                    }
                });
            }
        });
    }

    @SuppressWarnings("unchecked")
    public final <T extends Pane> T getRoot() {
        return (T) this.root;
    }

    public final void addChild(Node child) {
        root.getChildren().add(child);
    }

    public final void addChildren(Node... children) {
        root.getChildren().addAll(children);
    }
}
