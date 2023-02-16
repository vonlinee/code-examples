package io.devpl.tookit.fxui.common;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GlobalStage extends Stage {

    /**
     * 同一个Scene被设置到了新的Stage，那么原来的Scene将不能点击
     * 且原来的Scene会与原来的Stage解绑
     * @param root 根节点
     */
    public void setRoot(Parent root) {
        Scene scene = root.getScene();
        if (scene == null) {
            setScene(new Scene(root));
        } else if (scene != getScene()) { // 和之前的Scene不同
            setScene(scene);
        }
    }
}
