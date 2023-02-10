package io.devpl.toolkit.fxui.app;

import io.devpl.toolkit.fxui.view.filestructure.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class TestJavaFileStructure extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        BorderPane root = new BorderPane();
        JavaFileStrucutreTreeView jfsTreeView = new JavaFileStrucutreTreeView();
        Button btnAdd = new Button("导入");
        Button btn1 = new Button("B1");

        ToolBar toolBar = new ToolBar(btnAdd, btn1);
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

        root.setTop(toolBar);
        root.setLeft(jfsTreeView);

        JavaElementDetailPane detailPane = new JavaElementDetailPane();

        root.setCenter(detailPane);

        detailPane.addEventFilter(FileStructureEvent.CELL_UPDATED, event -> {
            System.out.println(1111);
        });

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
