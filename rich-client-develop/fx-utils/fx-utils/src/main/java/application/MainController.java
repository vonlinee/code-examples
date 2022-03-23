package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public BorderPane textHandlePane;
    public VBox topRootVBox;
    public HBox bottomHBox;
    public VBox centerVBox;
    public HBox centerHBox1;
    public HBox centerHBox2;
    public HBox centerHBox3;
    public Button chooseInputBtn;
    public Button chooseOutputBtn;
    public TextField inputFileTextField;
    public TextField outputFileTextField;
    public TextField choseFileTextField;
    public Button choseFileBtn;
    public Button copyToClipboardBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
