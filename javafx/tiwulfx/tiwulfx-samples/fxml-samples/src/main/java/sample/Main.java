package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    // 遮罩层
    Region region;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        final Button btn = new Button("Loading");

        final Button btnRemove = new Button("Remove");

        final Button btn1 = new Button("B1");

        ToolBar toolBar = new ToolBar(btn, btnRemove, btn1);

        root.setTop(toolBar);
        final TableView<Object> tableView = new TableView<>();
        root.setCenter(tableView);
        final Scene scene = new Scene(root, 600, 500);

        Loading loading = Loading.wrap(tableView, root, (loading1, pane) -> {
            pane.setCenter(null);
            loading1.getChildren().add(tableView);
            pane.setCenter(loading1);
        });

        btn.setOnAction(event -> loading.show());

        btn1.setOnAction(event -> {
            System.out.println(root.getChildren().contains(tableView));
        });

        btnRemove.setOnAction(event -> loading.hide());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
