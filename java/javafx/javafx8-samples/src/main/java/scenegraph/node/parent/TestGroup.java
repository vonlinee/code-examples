package scenegraph.node.parent;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestGroup extends Application {

    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        Group center = new Group();
        center.setStyle("-fx-background-color: #76d276");
        root.setCenter(center);
        Button btn1 = new Button("Button1");
        btn1.setPrefSize(100.0, 100.0);
        Button btn2 = new Button("Button2");
        btn2.setPrefSize(200.0, 200.0);
        btn2.setOpacity(0.5);
        Button btn3 = new Button("Button3");
        btn3.setOpacity(0.2);
        btn3.setPrefSize(300.0, 300.0);
        center.getChildren().addAll(btn1, btn2, btn3);

        ToolBar toolBar = new ToolBar();
        root.setTop(toolBar);
        Button autoSizeFalse = new Button("setAutoSizeChildren(false)");
        Button autoSizeTrue = new Button("setAutoSizeChildren(true)");
        toolBar.getItems().addAll(autoSizeFalse, autoSizeTrue);

        autoSizeFalse.setOnAction(event -> {
            center.setAutoSizeChildren(false);
            center.layout();
        });
        autoSizeTrue.setOnAction(event -> {
            center.setAutoSizeChildren(true);
            center.layout();
        });

        Scene scene = new Scene(root, initialWidth, initialHeight);
        primaryStage.setScene(scene);
        primaryStage.show();

        HBox.setMargin(center, new Insets(6));
    }
}
