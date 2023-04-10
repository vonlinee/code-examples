package io.devpl.tookit.fxui.app;

import io.devpl.tookit.fxui.view.components.OptionTableView;
import io.fxtras.JavaFXApplication;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestApp extends JavaFXApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {

        final OptionTableView view = new OptionTableView();

        primaryStage.setScene(new Scene(view, 600, 400));
        primaryStage.show();
    }
}
