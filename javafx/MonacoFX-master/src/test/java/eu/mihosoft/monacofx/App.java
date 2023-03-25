package eu.mihosoft.monacofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // create a new monaco editor node
        MonacoFX monacoFX = new MonacoFX();

        BorderPane root = new BorderPane();

        StackPane stackPane = new StackPane(monacoFX);
        root.setCenter(stackPane);

        Button btn1 = new Button("B1");
        ToolBar toolBar = new ToolBar(btn1);
        root.setTop(toolBar);

        btn1.setOnAction(event -> {
            String text = monacoFX.getEditor().getDocument().getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(text);
            alert.showAndWait();
        });

        // set initial text
        monacoFX.getEditor().getDocument().setText(
                "#include <stdio.h>\n" +
                "int main() {\n" +
                "   // printf() displays the string inside quotation\n" +
                "   printf(\"Hello, World!\");\n" +
                "   return 0;\n" +
                "}");

        // use a predefined language like 'c'
        monacoFX.getEditor().setCurrentLanguage("c");
        monacoFX.getEditor().setCurrentTheme("vs");

        // the usual scene & stage setup
        Scene scene = new Scene(root, 800,600);
        primaryStage.setTitle("MonacoFX Demo (running on JDK " + System.getProperty("java.version") + ")");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}