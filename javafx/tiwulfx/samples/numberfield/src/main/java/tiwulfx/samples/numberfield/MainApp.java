package tiwulfx.samples.numberfield;

import com.panemu.tiwulfx.control.NumberField;
import java.math.BigDecimal;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author amrullah
 */
public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		Label lbl1 = new Label("Integer");
		NumberField<Integer> txtInt = new NumberField<>(Integer.class);

		Label lblLong = new Label("Long");
		NumberField<Long> txtLong = new NumberField<>(Long.class);

		Label lbl2 = new Label("Double (Default)");
		NumberField<Double> txtDouble1 = new NumberField<>(Double.class);

		Label lbl3 = new Label("Double (2 digits before decimal, 3 digit behind decimal)");
		NumberField<Double> txtDouble2 = new NumberField<>(Double.class);
		txtDouble2.setMaxLength(2);
		txtDouble2.setDigitBehindDecimal(3);

		Label lbl4 = new Label("BigDecimal");
		NumberField<BigDecimal> txtBigDecimal = new NumberField<>(BigDecimal.class);

		VBox root = new VBox();
		root.setSpacing(10);
		root.setFillWidth(false);
		root.setPadding(new Insets(5));
		root.getChildren().addAll(lbl1, txtInt, lblLong, txtLong, lbl2, txtDouble1, lbl3, txtDouble2, lbl4, txtBigDecimal);

		Scene scene = new Scene(root, 500, 450);

		primaryStage.setTitle("Number Fields");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}


}
