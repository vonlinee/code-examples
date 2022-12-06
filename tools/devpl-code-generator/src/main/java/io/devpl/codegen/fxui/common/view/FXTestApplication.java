package io.devpl.codegen.fxui.common.view;

import io.devpl.codegen.fxui.framework.JFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXTestApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        HBox hBox = new HBox();
        hBox.getChildren();
        TextArea area = new TextArea();
        root.setTop(hBox);
        root.setCenter(area);
        area.setFont(Font.font(10));

        Scene scene = new Scene(root, 600, 400);

        Button btn = JFX.addButton(hBox, "打开", event -> {
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
