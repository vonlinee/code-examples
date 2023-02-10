package scenegraph.node.parent.pane.stackpane;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    List<String> styles = new ArrayList<>();

    int i = 0;

    double w = 50.0;
    double h = 50.0;

    @Override
    public void init() throws Exception {
        styles.add("-fx-background-color: red;");
        styles.add("-fx-background-color: green;");
        styles.add("-fx-background-color: yellow;");
        styles.add("-fx-background-color: blue;");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();

        ToolBar toolBar = new ToolBar();
        borderPane.setTop(toolBar);
        StackPane stackPane = new StackPane();
        borderPane.setCenter(stackPane);
        Button btn1 = new Button("Add");
        Button btn2 = new Button("Remove");
        Button btn3 = new Button("0");
        toolBar.getItems().addAll(btn1, btn2, btn3);

        btn1.setOnAction(event -> {
            stackPane.getChildren().add(newPane());
        });
        btn2.setOnAction(event -> {
            ObservableList<Node> children = stackPane.getChildren();
            final int size = children.size();
            if (size == 0) {
                return;
            }
            children.remove(size - 1);
            System.out.println(Thread.currentThread().getName());
        });
        stackPane.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                btn3.setText("" + c.getList().size());
            }
        });

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Pane newPane() {
        AnchorPane anchorPane = new AnchorPane();
        if (i == styles.size()) {
            i = 0;
        }
        anchorPane.setStyle(styles.get(i++));
        anchorPane.setPrefWidth(w += 50);
        anchorPane.setPrefHeight(h += 50);
        return anchorPane;
    }
}
