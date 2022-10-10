package io.devpl.codegen.fxui;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Text text = new Text();
        text.setText("1");
        text.setTextOrigin(VPos.BASELINE);

        HBox hBox = new HBox();
        hBox.getChildren().add(text);

        Text text2 = new Text();
        text.setText("2");
        text.setTextOrigin(VPos.BASELINE);
        hBox.getChildren().add(text2);

        text2.setX(100);
        text2.setY(50);
        BorderPane root = new BorderPane();

        System.out.println(hBox.getChildren());

        root.setTop(hBox);

        Scene scene = new Scene(root, 200, 200);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
