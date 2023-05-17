package tiwulfx.samples.lookupfield;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.LookupField;
import com.panemu.tiwulfx.control.LookupFieldController;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.TableControl;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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
		
		Label lbl1 = new Label("Type into LookupField to filter the options.");
		Label lbl2 = new Label("Press Ctrl + Enter to show lookup window.");
		Label lbl3 = new Label("Press Ctrl + Space to show suggestion.");

		List<Option> lst = new ArrayList<>();
		LookupField<Option> lookupField = new LookupField<>();
		lookupField.setPropertyName("label");
		Option opt = new Option(1, "One");
		lst.add(opt);
		opt = new Option(2, "Two");
		lst.add(opt);
		opt = new Option(3, "Three");
		lst.add(opt);
		opt = new Option(4, "Four");
		lst.add(opt);
		opt = new Option(5, "Five");
		lst.add(opt);
		opt = new Option(6, "Six");
		lst.add(opt);
		opt = new Option(7, "Seven");
		lst.add(opt);
		opt = new Option(17, "Seventeen");
		lst.add(opt);
		opt = new Option(70, "Seventy");
		lst.add(opt);
		
		lookupField.setController(new LookupFieldController<Option>(Option.class) {
			@Override
			public String[] getColumns() {
				return new String[]{"value", "label"};
			}
			
			@Override
			protected TableData loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingTypes, int maxResult) {
				List<Option> lstFiltered = new ArrayList<>();
				
				/**
				 * You should handle the filtering in the backend code instead of here.
				 * Take a look at advanced-demo for the example of backend filtering using hibernate.
				 */
				
				if (filteredColumns.isEmpty()) {
					lstFiltered.addAll(lst);
				} else {
					for (TableCriteria crit : filteredColumns) {
						if (crit.getOperator() == TableCriteria.Operator.eq) {
							for (Option option : lst) {
								if (option.getLabel().equals(crit.getValue())) {
									lstFiltered.add(option);
								}
							}
						} else if (crit.getOperator() == TableCriteria.Operator.ilike_anywhere) {
							for (Option option : lst) {
								if (option.getLabel().toUpperCase().contains(crit.getValue().toString().toUpperCase())) {
									lstFiltered.add(option);
								}
							}
						}
					}
				}
				//------------------------------------------
				
				return new TableData(lstFiltered, false, lstFiltered.size());
			}

			@Override
			protected void initCallback(VBox container, TableControl<Option> table) {
				super.initCallback(container, table);
				table.fillColumnsRecursively().get(0).setText("Value");
				table.fillColumnsRecursively().get(1).setText("Label");
				table.fillColumnsRecursively().get(1).setPrefWidth(300);
			}
			
			
		});

		Label lblFocused = new Label();
		lookupField.focusedProperty().addListener((ov, t, focused) -> {
			if (focused) {
				lblFocused.setText("FOCUSED");
			} else {
				lblFocused.setText("");
			}
		});
		HBox hbox = new HBox(lookupField, lblFocused);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		Button btn = new Button();
		btn.setText("Get Value");
		btn.setOnAction((ActionEvent event) -> {
			Option opt1 = lookupField.getValue();
			if (opt1 == null) {
				MessageDialogBuilder.info().message("No value selected").show(primaryStage);
			} else {
				MessageDialogBuilder.info().message("Selected value: " + opt1.toString()).show(primaryStage);
			}
		});
		
		Button btn2 = new Button("Set Value");
		btn2.setOnAction((t) -> {
			lookupField.setValue(lst.get(2));
		});
		
		
		VBox root = new VBox();
		root.setSpacing(10);
		root.setFillWidth(false);
		root.setPadding(new Insets(5));
		root.getChildren().addAll(lbl1, lbl2, lbl3, hbox, btn, btn2);

		Scene scene = new Scene(root, 800, 250);
		TiwulFXUtil.setTiwulFXStyleSheet(scene);
		primaryStage.setTitle("Lookup Field");
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
