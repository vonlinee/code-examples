package sample;

import io.devpl.fxtras.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXRouter.bind(this, primaryStage, "Hello World");
        configRoute();
        FXRouter.goTo("first");
    }

    private static void configRoute() {
        FXRouter.when("first", "fxml/first.fxml");
        FXRouter.when("second", "fxml/second.fxml");
        FXRouter.when("third", "fxml/third.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
