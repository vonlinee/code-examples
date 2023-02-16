package scenegraph;

import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestChoiceDialog extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FileChooserDialog fileChooserDialog = new FileChooserDialog();

        final List<String> choices = Arrays.asList("1", "2", "3");

        final ChoiceDialog<String> dialog = new ChoiceDialog<>("2", choices);
        fileChooserDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                final File selectedFile = fileChooserDialog.getSelectedFile();
                System.out.println(selectedFile);
            }
        });
    }
}
