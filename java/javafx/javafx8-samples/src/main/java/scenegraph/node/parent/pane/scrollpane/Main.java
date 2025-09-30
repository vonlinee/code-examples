package scenegraph.node.parent.pane.scrollpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();

        ToolBar toolBar = new ToolBar();

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(null);


        Button btn1 = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        toolBar.getItems().addAll(btn1, btn2, btn3);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
