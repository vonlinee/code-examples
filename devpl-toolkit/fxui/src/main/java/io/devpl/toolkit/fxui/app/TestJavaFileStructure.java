package io.devpl.toolkit.fxui.app;

import io.devpl.toolkit.fxui.utils.ResourceLoader;
import io.devpl.toolkit.fxui.view.filestructure.ClassItem;
import io.devpl.toolkit.fxui.view.filestructure.FieldItem;
import io.devpl.toolkit.fxui.view.filestructure.JavaFileStrucutreTreeView;
import io.devpl.toolkit.fxui.view.filestructure.MethodItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGLoader;

import java.net.MalformedURLException;

public class TestJavaFileStructure extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

        BorderPane root = new BorderPane();
        JavaFileStrucutreTreeView jfsTreeView = new JavaFileStrucutreTreeView();
        Button btnAdd = new Button("add");

        btnAdd.setGraphic(SVGLoader.load(ResourceLoader.load("static/icon/field.svg")));

        ToolBar toolBar = new ToolBar(btnAdd);
        btnAdd.setOnAction(event -> {
            ClassItem classItem = new ClassItem();
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

        root.setCenter(jfsTreeView);
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
