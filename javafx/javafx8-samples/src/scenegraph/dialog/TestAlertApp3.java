package scenegraph.dialog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.ResourceLoader;

public class TestAlertApp3 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Button btn = new Button("Button");
		
		btn.setOnAction(event -> {
			try {
				int i = 1 / 0;
			} catch (Exception exception) {
				ExceptionDialog.report(exception);
			}
		});
		Scene scene = new Scene(btn, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
