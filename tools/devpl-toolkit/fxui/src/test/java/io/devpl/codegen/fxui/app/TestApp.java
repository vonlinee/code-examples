package io.devpl.codegen.fxui.app;

import io.devpl.codegen.fxui.utils.FXMLHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pan1 = null, pan2 = null;
        FXMLLoader loader1 = FXMLHelper.createFXMLLoader("fxml/test1.fxml");
        FXMLLoader loader2 = FXMLHelper.createFXMLLoader("fxml/test2.fxml");
        pan1 = loader1.load();
        pan2 = loader2.load();
        pan1.setStyle("-fx-background-color: gray");
        VBox root = new VBox(pan1, pan2);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
