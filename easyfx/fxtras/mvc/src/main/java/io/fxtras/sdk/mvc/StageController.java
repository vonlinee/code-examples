package io.fxtras.sdk.mvc;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 提供访问当前所在Stage的能力
 */
public abstract class StageController extends WindowController<Stage> {

    private Scene scene;

    public final Scene getScene() {
        return scene;
    }

    public final void setScene(Scene scene) {
        this.scene = scene;
    }

    public final void setStage(Stage stage) {
        this.ownerWindow = stage;
    }

    public final Stage getStage() {
        return this.ownerWindow;
    }
}
