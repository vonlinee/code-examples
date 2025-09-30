package tools.file;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义文件选择
 */
public class TestChoiceDialog extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FileChooserDialog fileChooserDialog = new FileChooserDialog();
        fileChooserDialog.setInitialDirectory(new File(new File("").getAbsolutePath()));
        final List<String> choices = Arrays.asList("1", "2", "3");

        final ChoiceDialog<String> dialog = new ChoiceDialog<>("2", choices);
        fileChooserDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                // final File selectedFile = fileChooserDialog.getSelectedFile();
                JFXAlert<String> alert = new JFXAlert<>();
                alert.setAnimation(JFXAlertAnimation.SMOOTH);
                alert.show();
            }
        });
    }
}
