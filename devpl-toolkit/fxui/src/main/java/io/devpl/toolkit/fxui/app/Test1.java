package io.devpl.toolkit.fxui.app;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.MalformedURLException;

public class Test1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

        BorderPane root = new BorderPane();

        root.setStyle("-fx-background-color: #3ae7e7");

        TextField textField1 = TextFields.createClearableTextField();

        JFXTextField textField = new JFXTextField();
        root.setCenter(textField1);
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
