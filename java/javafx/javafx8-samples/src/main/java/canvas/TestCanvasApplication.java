package canvas;

import application.TestApplication;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestCanvasApplication extends Application {
	protected double initialWidth = 500.0;
	protected double initialHeight = 500.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * 创建根节点
	 * @return
	 * @throws Exception
	 */
	public Parent createRoot() throws Exception {
		Parent root = new AnchorPane();
		
		
		Canvas canvas = new Canvas();
		
		canvas.scaleXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
			}
		});
		
		return root;
	}
	
	public static void main(String[] args) {
		Application.launch(TestApplication.class, args);
	}
}
