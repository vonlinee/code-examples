package scenegraph.node.parent.control.textfield;

import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			TextField textField = new TextField();
			
			Button btn = new Button("Button");
			
			btn.setOnAction(event -> {
				Border border = textField.getBorder();
				Background background = textField.getBackground();
				System.out.println(border);
				System.out.println(background);
				
				List<BackgroundFill> fills = background.getFills();
				
				for (Iterator<BackgroundFill> iterator = fills.iterator(); iterator.hasNext();) {
					BackgroundFill backgroundFill = (BackgroundFill) iterator.next();
					
				}
			});
			
			HBox hBox = new HBox(textField, btn);

			Scene scene = new Scene(hBox, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
