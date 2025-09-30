package scenegraph.stage.contextmenu;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestContextMenuApplication extends Application {

    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
        primaryStage.setScene(scene);

        scene.getStylesheets().add("scenegraph/stage/contextmenu/context-menu.css");

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

        Button btn = new Button("ContextMenu");

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setPrefWidth(400);
        contextMenu.setPrefHeight(400);

        MenuItem settingMenuItem = new MenuItem("set");
        MenuItem updateMenuItem = new MenuItem("update");
        MenuItem feedbackMenuItem = new MenuItem("help");
        MenuItem aboutMenuItem = new MenuItem("File");
        MenuItem companyMenuItem = new MenuItem("about");

        contextMenu.getItems().add(settingMenuItem);
        contextMenu.getItems().add(updateMenuItem);
        contextMenu.getItems().add(companyMenuItem);
        contextMenu.getItems().add(feedbackMenuItem);
        contextMenu.getItems().add(aboutMenuItem);
        btn.setContextMenu(contextMenu);

        // 同一个 MenuItem 被添加到多个 ContextMenu 里，只会添加到最后一个

        final ContextMenu contextMenu1 = new ContextMenu();
        // contextMenu1.getItems().add(aboutMenuItem);

        Button btn1 = new Button("ContextMenu1");
        btn1.setContextMenu(contextMenu1);
        root.getChildren().add(btn);
        root.getChildren().add(btn1);

        AnchorPane.setLeftAnchor(btn1, 200.0);

        return root;
    }
}
