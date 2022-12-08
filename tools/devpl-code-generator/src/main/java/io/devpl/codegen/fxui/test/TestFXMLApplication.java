package io.devpl.codegen.fxui.test;

import io.devpl.codegen.fxui.framework.JavaFXApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class TestFXMLApplication extends JavaFXApplication {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getApplicationContext().getFxmlLocation("static/fxml/text_handle.fxml");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlLocation);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
