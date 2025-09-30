package scenegraph.node.parent.control;

import application.TestApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class TextFieldSample extends TestApplication {

	@Override
	public Parent createRoot(Stage stage) {
		
		FlowPane flowPane = new FlowPane();
		
		TextField textField = new TextField();
		
		
		Background background = new Background(new BackgroundFill(Paint.valueOf("#088"), new CornerRadii(20), new Insets(10)));
//		textField.setBackground(background);
		Border border = new Border(new BorderStroke(Paint.valueOf("#0ff"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0)));
		textField.setBorder(border);
		
//		textField.setOpacity(0.0);
		
		
		
		StackPane stackPane = new StackPane();
		stackPane.setStyle("-fx-background-color: #3ae7e7");
		stackPane.getChildren().add(textField);
		
		Label label = new Label("<>");
		
		
		stackPane.getChildren().add(label);
		
		textField.setOnMouseEntered(event -> {
			label.setVisible(true);
		});
		textField.setOnMouseExited(event -> {
			label.setVisible(false);
		});
		
		StackPane.setAlignment(label, Pos.CENTER_RIGHT);
		flowPane.getChildren().add(stackPane);
		flowPane.getChildren().add(new Button("Button"));
		
		
		return flowPane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
