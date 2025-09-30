package components.textfield;

import application.TestApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TestTextField extends TestApplication {

    @Override
    public Parent createRoot(Stage stage) throws Exception {

        AnchorPane root = new AnchorPane();

        final TextField textField = new TextField();

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(textField.getWidth());
            System.out.println(textField.getHeight());
        });
        textField.setPrefHeight(18.0);
        final Font font = textField.getFont();

        System.out.println(textField.getContextMenu());

        textField.setStyle("-fx-border-radius: 0; -fx-background-insets: 0; -fx-border-width: 0;-fx-border-color: black");


        root.getChildren().add(textField);

        AnchorPane.setLeftAnchor(textField, 10.0);

        final TextArea textArea = new TextArea();

        AnchorPane.setTopAnchor(textArea, 200.0);
        AnchorPane.setLeftAnchor(textArea, 10.0);
        root.getChildren().add(textArea);

        root.getStylesheets().add(getResourceAsURL("style.css").toExternalForm());

        return root;
    }

    public static void main(String[] args) {
        launch();
    }
}
