package library.jfoenix.navigation;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.jfoenix.navigation.main.MainController;
import utils.FxmlLoader;

public class Launcher extends Application {
    
    public static boolean isSplashLoaded = false;
    
    @Override
    public void start(Stage stage) throws Exception {
    	
    	
    	Parent root = FxmlLoader.load(MainController.class, "main.scenegraph.fxml");
    	
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Genuine Coder");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
