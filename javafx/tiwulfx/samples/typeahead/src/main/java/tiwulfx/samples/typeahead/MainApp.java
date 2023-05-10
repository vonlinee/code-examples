package tiwulfx.samples.typeahead;

import com.panemu.tiwulfx.control.TypeAheadField;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author amrullah
 */
public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		Label lbl1 = new Label("Type into TypeAheadField to filter the options.");
		Label lbl2 = new Label("Use Up/Down arrow to select an item then press Enter.");
		Label lbl3 = new Label("Pressing Tab keyboard key will select highlighted item and move focus to the next component.");
		Label lbl4 = new Label("Move caret position will change the filter.");

		TypeAheadField<Option> cmb = new TypeAheadField<>();
		Option opt = new Option(1, "One");
		cmb.addItem(opt.label, opt);
		opt = new Option(2, "Two");
		cmb.addItem(opt.label, opt);
		opt = new Option(3, "Three");
		cmb.addItem(opt.label, opt);
		opt = new Option(4, "Four");
		cmb.addItem(opt.label, opt);
		opt = new Option(5, "Five");
		cmb.addItem(opt.label, opt);
		opt = new Option(6, "Six");
		cmb.addItem(opt.label, opt);
		opt = new Option(7, "Seven");
		cmb.addItem(opt.label, opt);
		opt = new Option(17, "Seventeen");
		cmb.addItem(opt.label, opt);
		opt = new Option(70, "Seventy");
		cmb.addItem(opt.label, opt);

		Label lblFocused = new Label();
		cmb.focusedProperty().addListener((ov, t, focused) -> {
			if (focused) {
				lblFocused.setText("FOCUSED");
			} else {
				lblFocused.setText("");
			}
		});
		HBox hbox = new HBox(cmb, lblFocused);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		Button btn = new Button();
		btn.setText("Get Value");
		btn.setOnAction((ActionEvent event) -> {
			Option opt1 = cmb.getValue();
			if (opt1 == null) {
				MessageDialogBuilder.info().message("No value selected").show(primaryStage);
			} else {
				MessageDialogBuilder.info().message("Selected value: " + opt1.toString()).show(primaryStage);
			}
		});
		
		Button btn2 = new Button("Set Value");
		btn2.setOnAction((t) -> {
			cmb.setValue(cmb.getItems().get(2));
		});
		
		
		VBox root = new VBox();
		root.setSpacing(10);
		root.setFillWidth(false);
		root.setPadding(new Insets(5));
		root.getChildren().addAll(lbl1, lbl2, lbl3, lbl4, hbox, btn, btn2);

		Scene scene = new Scene(root, 800, 250);

		primaryStage.setTitle("TypeAheadField");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private static class Option {

		int value;
		String label;

		public Option(int value, String label) {
			this.value = value;
			this.label = label;
		}

		@Override
		public String toString() {
			return "Option{" + "value=" + value + ", label=" + label + '}';
		}
	}
}
