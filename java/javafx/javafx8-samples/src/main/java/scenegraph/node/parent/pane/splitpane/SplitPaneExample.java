package scenegraph.node.parent.pane.splitpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SplitPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        SplitPane splitPane = new SplitPane();

        VBox leftControl  = new VBox(new Label("Left Control"));
        VBox rightControl = new VBox(new Label("Right Control"));

        splitPane.getItems().addAll(leftControl, rightControl);

        Scene scene = new Scene(splitPane, 500.0, 500.0);

        scene.getStylesheets().add("scenegraph/node/parent/pane/splitpane/splipane.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX App");

        primaryStage.show();
    }
}