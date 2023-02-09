package scenegraph.node.parent.control.checkbox;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestCheckBoxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CheckBox checkBox = new CheckBox();
        Label label = new Label("CheckBox");

        HBox hBox = new HBox(checkBox, label);
        hBox.setSpacing(4);

        VBox box = new VBox(hBox);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
