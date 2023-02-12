package io.devpl.toolkit.fxui.app;

import io.devpl.toolkit.fxui.view.filestructure.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestJavaFileStructure extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        JavaFileStrucutreTreeView jfsTreeView = new JavaFileStrucutreTreeView();

        Button btnAdd = new Button("导入");
        btnAdd.setOnAction(event -> {
            TopLevelClassItem classItem = new TopLevelClassItem();
            classItem.setValue("Student");

            MethodItem methodItem = new MethodItem();
            methodItem.setValue("setName");
            classItem.addMethod(methodItem);

            FieldItem fieldItem = new FieldItem();
            fieldItem.setValue("name");
            classItem.addField(fieldItem);

            jfsTreeView.addClass(classItem);
        });
        root.setTop(new ToolBar(btnAdd));

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(jfsTreeView);
        root.setCenter(splitPane);

        JavaElementDetailPane detailPane = new JavaElementDetailPane();
        detailPane.setStyle("-fx-background-color: red");
        splitPane.getItems().add(detailPane);

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
