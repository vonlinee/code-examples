package io.devpl.toolkit.fxui.test;

import io.devpl.toolkit.fxui.framework.JavaFXApplication;
import io.devpl.sdk.util.ResourceUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestFXMLApplication extends JavaFXApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader.load(ResourceUtils.getProjectResource("static/fxml/MainUI.fxml"));

        Scene scene = new Scene(new BorderPane());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
