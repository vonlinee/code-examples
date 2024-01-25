package apps.maventools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Label labMavenRootDir;
    @FXML
    public Label labInput;
    @FXML
    public TextField txf;
    @FXML
    public TextField txfUserInput;
    @FXML
    public Button btnSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txf.setText("D:\\Develop\\Tools\\MavenLocalRepo");
    }

    @FXML
    public void search(ActionEvent actionEvent) {
        String mavenRepoDir = txf.getText();
        String text = txfUserInput.getText();
    }
}
