package scenegraph.node;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestNode extends Application {

    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     *
     * @return
     * @throws Exception
     */
    public Parent createRoot() throws Exception {
        AnchorPane root = new AnchorPane();

        Button btn = new Button("Button");

        root.getChildren().add(btn);
        return root;
    }
}
