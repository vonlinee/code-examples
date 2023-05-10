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
        final StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: red");
        root.setBottom(stackPane);
        btn.setOnAction(event -> {
            region = LoadingUtils.loadingDefault(stackPane);
            System.out.println(region);
        });

        final Button btnRemove = new Button("Remove");
        btnRemove.setOnAction(event -> {
            System.out.println(stackPane.getChildren().size());
            System.out.println(LoadingUtils.remove(stackPane, region));
            System.out.println(stackPane.getChildren().size());

            System.out.println(stackPane.getWidth() + " " + stackPane.getHeight());
        });


        final Button btn1 = new Button("B1");

        ToolBar toolBar = new ToolBar(btn, btnRemove, btn1);

        root.setTop(toolBar);
        final TableView<Object> tableView = new TableView<>();
        root.setCenter(tableView);
        final Scene scene = new Scene(root, 600, 500);

        btn1.setOnAction(event -> {
            Loading loading = new Loading(tableView);
            System.out.println(loading.getWidth());
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
