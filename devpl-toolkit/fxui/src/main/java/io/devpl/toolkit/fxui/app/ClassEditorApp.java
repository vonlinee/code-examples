package io.devpl.toolkit.fxui.app;

import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.toolkit.fxui.controller.ClassDefinitionController;
import io.devpl.toolkit.fxui.controller.ConnectionManageController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClassEditorApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(ViewLoader.load(ClassDefinitionController.class)
                .getRoot());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
