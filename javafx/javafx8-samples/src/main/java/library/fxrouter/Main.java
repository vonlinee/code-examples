package library.fxrouter;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXRouter.bind(this, primaryStage, "Hello World", 300, 275);    // bind FXRouter
        FXRouter.when("firstPage", "sample.fxml");                     // set "firstPage" route
        FXRouter.goTo("firstPage");                                    // switch to "sample.fxml"
    }

    public static void main(String[] args) {
        launch(args);
    }
}