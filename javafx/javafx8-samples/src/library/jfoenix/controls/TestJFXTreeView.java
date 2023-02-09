package library.jfoenix.controls;

import com.jfoenix.controls.JFXTreeView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

public class TestJFXTreeView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), 600.0, 500.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     * @return
     * @throws Exception
     */
    public Parent createRoot() throws Exception {
        JFXTreeView<String> treeView = new JFXTreeView<>();
        treeView.setRoot(new TreeItem<>());
        treeView.setShowRoot(false);

        treeView.getRoot().getChildren().add(new TreeItem<>("1"));
        treeView.getRoot().getChildren().add(new TreeItem<>("2"));
        treeView.getRoot().getChildren().add(new TreeItem<>("3"));

        return treeView;
    }
}
