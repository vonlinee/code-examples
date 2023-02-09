package scenegraph.node.parent.pane.vbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TestHBoxVBox extends Application {

	public static void main(String[] args) {
		launch();
	}

	public void start(Stage primaryStage) throws Exception {
		Button button = new Button("button");
		button.setFont(Font.font(20));

		HBox hbox = new HBox();
		hbox.getChildren().add(button);
		hbox.getChildren().add(new Button("button1"));
		hbox.getChildren().add(new Button("button2"));
		hbox.setStyle("-fx-background-color:#66ccff");

		
		
		
		Scene scene = new Scene(hbox);
		primaryStage.setScene(scene);
		primaryStage.setWidth(400);
		primaryStage.setHeight(400);
		primaryStage.show();
	}

}
