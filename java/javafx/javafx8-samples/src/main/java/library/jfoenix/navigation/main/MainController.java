package library.jfoenix.navigation.main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import library.jfoenix.navigation.Launcher;
import library.jfoenix.navigation.panel.SidePanelController;
import library.jfoenix.navigation.splash.SplashController;
import utils.FxmlLoader;

public class MainController implements Initializable, ColorChangeCallback {

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private AnchorPane root;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (!Launcher.isSplashLoaded) {
			loadSplashScreen();
		}

		try {
			
			FXMLLoader loader = FxmlLoader.getFxmlLoader(SidePanelController.class, "sidepanel.scenegraph.fxml");
				
			VBox box = loader.getRoot();
			SidePanelController controller = loader.getController();
			controller.setCallback(this);
			drawer.setSidePane(box);
		} catch (Exception ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}

		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isOpened()) {
				drawer.close();
			} else {
				drawer.open();
			}
		});
	}

	private void loadSplashScreen() {
		try {
			Launcher.isSplashLoaded = true;

			StackPane pane = FxmlLoader.load(SplashController.class, "splash.scenegraph.fxml");

			root.getChildren().setAll(pane);

			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);

			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			fadeIn.setOnFinished((e) -> {
				fadeOut.play();
			});

			fadeOut.setOnFinished((e) -> {
				AnchorPane parentContent = FxmlLoader.load(getClass(), "main.scenegraph.fxml");

				root.getChildren().setAll(parentContent);
			});

		} catch (Exception ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void updateColor(String newColor) {
		root.setStyle("-fx-background-color:" + newColor);
	}
}
