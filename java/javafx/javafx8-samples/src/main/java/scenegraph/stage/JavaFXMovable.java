package scenegraph.stage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 无边框可拖拽窗口
 */
public class JavaFXMovable extends Application {

	// define your offsets here
	private double xOffset = 0;
	private double yOffset = 0;

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = new BorderPane();
		// you can use underdecorated or transparent.
		stage.initStyle(StageStyle.TRANSPARENT);
		// stage.initStyle(StageStyle.UNDERDECORATED);

		// grab your root here
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		// move around here
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});

		Scene scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}