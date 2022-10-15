package io.devpl.codegen.fxui.boot;

import com.jfoenix.controls.JFXAlert;
import io.devpl.codegen.fxui.utils.Alerts;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
//            String text = fxmlLocaltionTextField.getText();
//            if (text != null && text.length() != 0) {
//                try {
//                    URL resource = Thread.currentThread().getContextClassLoader().getResource(text);
//                    FXMLLoader fxmlLoader = new FXMLLoader(resource);
//                    Parent parent = fxmlLoader.load();
//                    Stage stage = new Stage();
//                    stage.setScene(new Scene(parent));
//                    stage.show();
//                } catch (Exception exception) {
//                    Alerts.showError(exception.getMessage());
//                }
//            }
            Alerts.info("标题", "文本");

            JFXAlert<String> alert = new JFXAlert<>();
            alert.setContentText("sfffffffffffffffffffffffffffff");
            alert.showAndWait();
        });

        root.setCenter(vBox);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
