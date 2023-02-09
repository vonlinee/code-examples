package library.jfoenix.controls;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class TestTabPane extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createRoot(), 600.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     */
    public Parent createRoot() {
        JFXTabPane root = new JFXTabPane();

        Tab tab1 = new Tab("Tab1");
        Tab tab2 = new Tab("Tab2");
        Tab tab3 = new Tab("Tab3");
        Tab tab4 = new Tab("Tab4");

        ContextMenu contextMenu = new ContextMenu();

        contextMenu.getItems().add(new MenuItem("Menu1"));
        contextMenu.getItems().add(new MenuItem("Menu2"));
        contextMenu.getItems().add(new MenuItem("Menu3"));

        tab1.setContextMenu(contextMenu);

        root.getTabs().addAll(tab1, tab2, tab3, tab4);

        root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);


        root.setSide(Side.LEFT);

        // add code here
        return root;
    }
}
