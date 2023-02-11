package io.devpl.toolkit.fxui.app;

import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.toolkit.fxui.controller.ClassDefinitionController;
import io.devpl.toolkit.fxui.controller.MetaFieldManageController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 测试单个界面
 */
public class UnitTestApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(ViewLoader.load(MetaFieldManageController.class)
                .getRoot());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
