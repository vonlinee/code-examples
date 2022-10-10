package io.devpl.codegen.fxui.boot;

import io.devpl.codegen.fxui.utils.FXMLHelper;
import io.devpl.codegen.fxui.utils.FXUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader1 = FXMLHelper.createFXMLLoader("fxml/test1.fxml");
        FXMLLoader loader2 = FXMLHelper.createFXMLLoader("fxml/test2.fxml");
        AnchorPane pan1 = loader1.load();
        pan1.setStyle("-fx-background-color: gray");
        AnchorPane pan2 = loader2.load();
        pan2.setStyle("-fx-background-color: black");

        VBox root = new VBox(pan1, pan2);

        Scene scene = new Scene(root, 200, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
