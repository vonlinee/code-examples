package code.fxutils.core.extension;

import code.fxutils.core.mvc.view.app.TextHandlerFrame;
import code.fxutils.core.mvc.view.control.SettingsFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainFrame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        Button btn = new Button("Button");
        btn.setOnAction(event -> {
            TextHandlerFrame frame = new TextHandlerFrame();
            frame.show();
        });
        hBox.getChildren().add(btn);
        borderPane.setTop(hBox);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }
}
