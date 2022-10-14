package io.devpl.codegen.fxui.view.control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApp extends Application {

    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("张三", 10, "男", "123123@qq.com"),
            new Person("李四", 90, "女", "123321@qq.com"),
            new Person("王富贵", 80, "男", "321123@qq.com")
    );

    @Override
    public void start(Stage primaryStage) throws Exception {



        //这个data是数据源
        TableView<Person> table = new TableView<>(data);
        //table可以可以作为成员变量

        TableColumn<Person, String> nameCol = new TableColumn<>();
        TableColumn<Person, Integer> ageCol = new TableColumn<>();
        TableColumn<Person, String> sexCol = new TableColumn<>();
        TableColumn<Person, String> emailCol = new TableColumn<>();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        BorderPane borderPane = new BorderPane(table);

        primaryStage.setScene(new Scene(borderPane, 600, 600));
    }
}
