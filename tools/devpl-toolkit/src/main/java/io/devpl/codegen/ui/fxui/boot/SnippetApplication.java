package io.devpl.codegen.ui.fxui.boot;

import io.devpl.codegen.ui.fxui.utils.AlertDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class SnippetApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        Button btn = new Button("show");
        TextField fxmlLocaltionTextField = new TextField();
        HBox hBox = new HBox(fxmlLocaltionTextField, btn);
        vBox.getChildren().add(hBox);

        fxmlLocaltionTextField.prefWidthProperty().bind(root.widthProperty().subtract(btn.widthProperty()));

        btn.setOnMouseClicked(event -> {
            String text = fxmlLocaltionTextField.getText();
            if (text != null && text.length() != 0) {
                try {
                    URL resource = Thread.currentThread().getContextClassLoader().getResource(text);
                    FXMLLoader fxmlLoader = new FXMLLoader(resource);
                    Parent parent = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();
                } catch (Exception exception) {
                    AlertDialog.showError(exception.getMessage());
                }
            }
        });

        root.setCenter(vBox);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
