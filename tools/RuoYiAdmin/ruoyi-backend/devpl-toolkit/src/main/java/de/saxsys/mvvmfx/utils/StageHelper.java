package de.saxsys.mvvmfx.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StageHelper {

    /**
     * 通过节点获取场景图，如果节点未被绑定到场景图上，创建一个新的场景图以及舞台
     * @param rootNode 节点
     */
    public static void show(Parent rootNode) {
        if (rootNode == null) {
            return;
        }
        Scene scene = rootNode.getScene();
        Stage stage;
        if (scene == null) {
            stage = newDefaultStage(400, 400);
            stage.setScene(new Scene(rootNode));
        } else {
            Window window = scene.getWindow();
            if (window instanceof Stage) {
                stage = (Stage) window;
            } else {
                stage = new Stage();
            }
        }
        stage.show();
    }

    public static Stage newDefaultStage(double width, double height) {
        Stage stage = new Stage();
        stage.setTitle("默认窗口");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(true);
        stage.setWidth(width);
        stage.setHeight(height);
        return stage;
    }
}
