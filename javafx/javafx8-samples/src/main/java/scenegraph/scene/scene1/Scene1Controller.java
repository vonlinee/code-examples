package scenegraph.scene.scene1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import scenegraph.scene.scene2.Scene2Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Genuine Coder
 */
public class Scene1Controller implements Initializable {

    @FXML
    private TextField inputField;
    @FXML
    private Button actionBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionBtn.setOnAction(event -> {
            loadSceneAndSendMessage();
        });
    }
    
    public void receiveMessage(String message){
        
    }

    private void loadSceneAndSendMessage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/node/scene/javafxcontrollercommunication/scene2/scene2.scenegraph.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            Scene2Controller scene2Controller = loader.getController();
            scene2Controller.transferMessage(inputField.getText());
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
