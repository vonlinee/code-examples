/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo.ui;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.demo.DaoBase;
import com.panemu.tiwulfx.demo.TiwulfxDemo;
import com.panemu.tiwulfx.demo.misc.DataGenerator;
import com.panemu.tiwulfx.demo.pojo.Insurance;
import com.panemu.tiwulfx.demo.pojo.Person;

import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialog.Answer;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.form.Form;
import com.panemu.tiwulfx.table.ButtonColumn;
import com.panemu.tiwulfx.table.ComboBoxColumn;
import com.panemu.tiwulfx.table.LookupColumn;
import com.panemu.tiwulfx.table.NumberColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;
import com.panemu.tiwulfx.table.TypeAheadColumn;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
public class FrmPersonTable2 extends VBox {

	@FXML
	private TextArea txtInformation;
	@FXML
	protected TableControl<Person> tblPerson;
	@FXML
	private TypeAheadColumn<Person, String> clmBirthPlace;
	@FXML
	private ComboBoxColumn<Person, Character> clmGender;
	@FXML
	private LookupColumn<Person, Insurance> clmInsurance;
	@FXML
	private ButtonColumn<Person> clmButton;
	@FXML
	private NumberColumn<Person, Integer> clmVisit;
	@FXML
	private NumberColumn<Person, Integer> clmInsuranceId;
	@FXML
	private NumberColumn<Person, Integer> clmVersion;
	private DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);

	public FrmPersonTable2() {
		FXMLLoader fxmlLoader = new FXMLLoader(FrmPersonTable.class.getResource("FrmPersonTable.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setResources(TiwulFXUtil.getLiteralBundle());
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		init();
		tblPerson.reloadFirstPage();
	}

	protected void init() {
		tblPerson.setRecordClass(Person.class);
		tblPerson.setController(controller);
		tblPerson.setMaxRecord(50);

		for (String location : DataGenerator.birthPlaces) {
			clmBirthPlace.addItem(location, location);
		}

		clmGender.addItem("Male", 'm');
		clmGender.addItem("Female", 'f');

		clmInsuranceId.setNumberType(Integer.class);

		clmVisit.setNumberType(Integer.class);
		clmVersion.setNumberType(Integer.class);

		// Add button in TableControl's toolbar
		Button button = new Button();
		button.setGraphic(new ImageView(new Image(TableControl.class.getResourceAsStream("/images/chart.png"))));

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tblPerson.refresh();
				Answer answer = MessageDialogBuilder.info().message("message.one")
						  .yesOkButtonText("I know")
						  .noButtonText("No, I don't")
						  .buttonType(MessageDialog.ButtonType.YES_NO)
						  .show(FrmPersonTable2.this.getScene().getWindow());
				if (answer == Answer.NO) {
					MessageDialogBuilder.info().message("Maybe the developer hid it.")
							  .show(FrmPersonTable2.this.getScene().getWindow());
				}
			}
		});
		tblPerson.addButton(button);

		// Hide delete button
		tblPerson.setVisibleComponents(false, TableControl.Component.BUTTON_DELETE);

		// Add menu item to TableControl's context menu
		MenuItem customMI = new MenuItem("Custom Menu Item");
		customMI.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//Show the MessageDialog later to give a chance for popup to close it self.
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						MessageDialogBuilder.warning().buttonType(MessageDialog.ButtonType.YES_NO_CANCEL)
								  .title("Custom Context Menu Item")
								  .message("You just clicked custom context menu item.")
								  //                        .yesOkButtonText("Yes Text")
								  .noButtonText("no.text")
								  .cancelButtonText("Cancel Text")
								  .show(FrmPersonTable2.this.getScene().getWindow());
					}
				});
			}
		});

		tblPerson.addContextMenuItem(customMI);
		txtInformation.setText("This form provide an implementation example of editing or inserting records in a separate form.");
	}

	private TableController<Person> controller = new TableController<Person>() {

		private DaoBase<Person> daoPerson = new DaoBase<>(Person.class);
		private Stage dialogStage;// = new Stage();
		private FrmPersonMaintenance frmPersonMaintenance;

		@Override
		public TableData loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
			TableData result = daoPerson.fetch(startIndex, filteredColumns, sortedColumns, sortingOrders, maxResult, Arrays.asList("insurance"));
			return result;
		}

		@Override
		public Person preInsert(Person newRecord) {
			showFrmPersonMaintenance(newRecord, Form.Mode.INSERT);
			return null;
		}

		@Override
		public boolean canEdit(Person selectedRecod) {
			if (selectedRecod == null) {
				MessageDialogBuilder.error().message("Please select a record to edit.").show(null);
				return false;
			}
			showFrmPersonMaintenance(selectedRecod, Form.Mode.EDIT);
			return false;
		}

		@Override
		public void doubleClick(Person record) {
			showFrmPersonMaintenance(record, Form.Mode.READ);
		}

		@Override
		public void delete(List<Person> records) {
			daoPerson.delete(records);
		}

		private void showFrmPersonMaintenance(Person person, Form.Mode mode) {
			if (dialogStage == null) {
				frmPersonMaintenance = new FrmPersonMaintenance();
				/**
				 * cannot instantiate dialogStage when instantiating this class because it's done in non-FX thread. It turns that instantiating stage should be in FX thread..
				 */
				dialogStage = new Stage();
				dialogStage.initOwner(TiwulfxDemo.mainStage);
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.setScene(new Scene(frmPersonMaintenance));
				TiwulFXUtil.setTiwulFXStyleSheet(dialogStage.getScene());
			}
			frmPersonMaintenance.setPerson(person);
			frmPersonMaintenance.setMode(mode);
			dialogStage.show();
		}

	};

}
