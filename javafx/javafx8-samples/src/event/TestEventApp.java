package event;

import application.TestApplication;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TestEventApp extends TestApplication {

    @Override
    public Parent createRoot(Stage primaryStage) {

        Button btn = new Button("Button");

        btn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("EventFilter 鼠标点击");
            event.consume();
        });
        btn.setOnMouseClicked(event -> {
            System.out.println("鼠标点击");
        });

        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
