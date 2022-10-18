package io.devpl.codegen.fxui.app;

import io.devpl.codegen.fxui.utils.FXMLHelper;
import io.devpl.codegen.fxui.utils.FxmlLoadResult;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

public class SnippetApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        Button btn = new Button("show");
        TextField fxmlLocaltionTextField = new TextField();
        HBox hBox = new HBox(fxmlLocaltionTextField, btn);
        vBox.getChildren().add(hBox);

        fxmlLocaltionTextField.prefWidthProperty().bind(root.widthProperty().subtract(btn.widthProperty()));


        StatusBar statusBar = new StatusBar();
        vBox.getChildren().add(statusBar);
        btn.setOnMouseClicked(event -> {
            FxmlLoadResult result = FXMLHelper.loadFxml("fxml/other/work_record.fxml");
            Scene scene = new Scene(result.getRoot());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });

        root.setCenter(vBox);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
