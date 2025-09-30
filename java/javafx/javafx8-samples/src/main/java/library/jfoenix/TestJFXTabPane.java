package library.jfoenix;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.skins.JFXTabPaneSkin;

import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestJFXTabPane extends Application {

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
		JFXTabPane root = new JFXTabPane();
		
		TabPane tabPane = new TabPane();

		Tab tab1 = new Tab("Tab1");
		tab1.setContent(new AnchorPane());
		
		Tab tab2 = new Tab("Tab2");
		tab1.setContent(new AnchorPane());
		
		root.getTabs().add(tab1);
		root.getTabs().add(tab2);
		
		
		return root;
	}
	
	public static void main(String[] args) {
		Application.launch(TestJFXTabPane.class, args);
	}

}
