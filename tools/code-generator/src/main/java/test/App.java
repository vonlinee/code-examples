package test;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setScene(new Scene(createContent()));

        primaryStage.show();
    }

    private Parent createContent() {
        Group group = new Group();
        JFXTextArea area = new JFXTextArea();
        area.setPrefSize(400.0, 400.0);
        group.getChildren().add(area);
        area.setStyle("-fx-background-color: red");
        group.getChildren().add(new Button("report"));
        return group;
    }
}
