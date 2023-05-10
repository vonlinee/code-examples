package tiwulfx.samples.localization;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		// add default literal (english)
		TiwulFXUtil.addLiteralBundle("tiwulfx.samples.localization.res.literal");
		FrmEnglish frmData = new FrmEnglish();
		Scene scene = new Scene(frmData);
		TiwulFXUtil.setTiwulFXStyleSheet(scene);
		stage.setTitle("Default (English)");
		stage.setScene(scene);
		stage.setX(100);
		stage.setY(50);
		stage.show();

		//add indonesian literal
		Locale locIndonesian = new Locale("in", "ID");
		TiwulFXUtil.setLocale(locIndonesian);
		TiwulFXUtil.addLiteralBundle("tiwulfx.samples.localization.res.literal");

		// display FrmInd
		FrmInd frmInd = new FrmInd();
		Stage stage2 = new Stage();
		stage2.setScene(new Scene(frmInd));
		stage2.setTitle("Indonesian");
		stage2.setX(400);
		stage2.setY(250);
		stage2.show();

		// add arabic literal
		Locale locArabic = new Locale("ar", "SA");
		TiwulFXUtil.setLocale(locArabic);
		TiwulFXUtil.addLiteralBundle("tiwulfx.samples.localization.res.literal");

		// display FrmInd
		FrmArabic frmArabic = new FrmArabic();
		Stage stage3 = new Stage();
		stage3.setScene(new Scene(frmArabic));
		stage3.setTitle("Arabic");
		stage3.setX(700);
		stage3.setY(450);
		stage3.show();

		init();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
