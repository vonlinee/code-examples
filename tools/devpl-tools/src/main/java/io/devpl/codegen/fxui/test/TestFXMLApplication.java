package io.devpl.codegen.fxui.test;

import io.devpl.codegen.fxui.framework.JavaFXApplication;
import io.devpl.codegen.fxui.framework.fxml.FXMLLoader;
import io.devpl.codegen.fxui.utils.ResourceUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class TestFXMLApplication extends JavaFXApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader.load(ResourceUtils.getProjectResource("static/fxml/MainUI.fxml"));

        Scene scene = new Scene(new BorderPane());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
