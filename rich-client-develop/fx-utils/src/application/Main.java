package application;

import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = FXMLLoader.load(Main.class.getResource("main.fxml").toURI().toURL());
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets()
					.add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}