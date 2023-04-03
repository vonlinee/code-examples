package eu.mihosoft.monacofx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        MonacoEditor monacoFX = new MonacoEditor();

        monacoFX.setStyle("-fx-background-color: red");

        BorderPane root = new BorderPane();

        TreeView<String> treeView = new TreeView<>(new TreeItem<>());

        treeView.getRoot().getChildren().add(new TreeItem<>("1"));
        treeView.getRoot().getChildren().add(new TreeItem<>("2"));
        treeView.getRoot().getChildren().add(new TreeItem<>("3"));

        root.setLeft(treeView);
        StackPane stackPane = new StackPane(monacoFX);
        root.setCenter(stackPane);

        stackPane.widthProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));

        stackPane.prefWidthProperty().bind(root.widthProperty().subtract(treeView.prefWidthProperty()));

        Button btn1 = new Button("B1");
        ToolBar toolBar = new ToolBar(btn1);
        root.setTop(toolBar);

        btn1.setOnAction(event -> {
//            String text = monacoFX.getEditor().getDocument().getText();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText(text);
//            alert.showAndWait();


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
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MonacoFX Demo (running on JDK " + System.getProperty("java.version") + ")");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}