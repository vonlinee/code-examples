/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo.ui;

import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.LookupFieldController;
import com.panemu.tiwulfx.demo.DaoBase;
import com.panemu.tiwulfx.demo.misc.DataGenerator;
import com.panemu.tiwulfx.demo.misc.EmailValidator;
import com.panemu.tiwulfx.demo.pojo.Insurance;
import com.panemu.tiwulfx.demo.pojo.Person;
import com.panemu.tiwulfx.form.CheckBoxControl;
import com.panemu.tiwulfx.form.ChoiceBoxControl;
import com.panemu.tiwulfx.form.ComboBoxControl;
import com.panemu.tiwulfx.form.DateControl;
import com.panemu.tiwulfx.form.Form;
import com.panemu.tiwulfx.form.Form.Mode;
import com.panemu.tiwulfx.form.LookupControl;
import com.panemu.tiwulfx.form.NumberControl;
import com.panemu.tiwulfx.form.TextControl;
import com.panemu.tiwulfx.table.TableControl;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
public class FrmPersonMaintenance extends BorderPane {

	@FXML
	private Button btnAdd;
	@FXML
	private Button btnEdit;
//    @FXML
//    private Button btnReload;
	@FXML
	private Button btnSave;
	@FXML
	private CheckBoxControl chkAlive;
	@FXML
	private ComboBoxControl<String> cmbBirthPlace;
	@FXML
	private ChoiceBoxControl<Character> cmbGender;
	@FXML
	private LookupControl<Insurance> lkupInsurance;
	@FXML
	private DateControl txtBirthDate;
	@FXML
	private TextControl txtEmail;
	@FXML
	private TextControl txtInsuranceName;
	@FXML
	private TextControl txtName;
	@FXML
	private NumberControl<Integer> txtVersion;
	@FXML
	private NumberControl<Integer> txtVisit;
	@FXML
	private NumberControl<Double> txtWeight;
	@FXML
	private Form<Person> personForm;
	private DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);
	private DaoBase<Person> daoPerson = new DaoBase<>(Person.class);

	public FrmPersonMaintenance() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FrmPersonMaintenance.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		initControls();
		initActionListener();
	}

	public void setPerson(Person person) {
		personForm.setRecord(person);
	}

	private void initControls() {

		txtEmail.addValidator(new EmailValidator());

		for (String location : DataGenerator.birthPlaces) {
			cmbBirthPlace.addItem(location, location);
		}
		cmbBirthPlace.setRequired(false);

		cmbGender.addItem("Male", 'm');
		cmbGender.addItem("Female", 'f');
		chkAlive.setRequired(false);
		chkAlive.setLabel(TiwulFXUtil.getLiteral("alive.true"), TiwulFXUtil.getLiteral("alive.false"), TiwulFXUtil.getLiteral("alive.null"));

		lkupInsurance.setController(insuranceLookupController);

		txtVisit.setNumberType(Integer.class);

		txtVersion.setNumberType(Integer.class);
		personForm.bindChildren();
	}

	private void initActionListener() {
		btnSave.setOnAction(eventHandler);
		btnEdit.setOnAction(eventHandler);
		btnAdd.setOnAction(eventHandler);
//        btnReload.setOnAction(eventHandler);

		btnSave.disableProperty().bind(personForm.modeProperty().isEqualTo(Form.Mode.READ));
		btnAdd.disableProperty().bind(personForm.modeProperty().isNotEqualTo(Form.Mode.READ));
		btnEdit.disableProperty().bind(personForm.modeProperty().isNotEqualTo(Form.Mode.READ));
	}

	public void setMode(Mode mode) {
		personForm.setMode(mode);
	}

	private EventHandler<ActionEvent> eventHandler = new EventHandler<>() {
		@Override
		public void handle(ActionEvent t) {
			if (t.getSource() == btnSave && personForm.validate()) {
				Person p = personForm.getRecord();
				if (p.getId() == null) {
					p = daoPerson.insert(p);
				} else {
					p = daoPerson.update(p);
					p = daoPerson.initRelationship(p, "insurance");
				}
				personForm.setRecord(p);
				personForm.setMode(Form.Mode.READ);
			} else if (t.getSource() == btnEdit) {
				personForm.setMode(Form.Mode.EDIT);
			} else if (t.getSource() == btnAdd) {
				personForm.setRecord(new Person());
				personForm.setMode(Form.Mode.INSERT);
//            } else if (t.getSource() == btnReload) {
//                personForm.setValueObject(person);
//                personForm.setMode(Form.Mode.READ);
//                personForm.validate();//ensure to remove exclamation mark next to the invalid fields
			}
		}
	};
	private LookupFieldController<Insurance> insuranceLookupController = new LookupFieldController<Insurance>(Insurance.class) {
		@Override
		public String[] getColumns() {
			return new String[]{
				"id",
				"code",
				"name"
			};
		}

		@Override
		protected void initCallback(VBox container, TableControl<Insurance> table) {
			container.setPrefWidth(500);
			table.getTableView().getColumns().get(2).setPrefWidth(300);
		}

		@Override
		protected String getWindowTitle() {
			return "Insurance";
		}

		@Override
		protected TableData loadData(int startIndex, List filteredColumns, List sortedColumns, List sortingTypes, int maxResult) {
			return daoInsurance.fetch(startIndex, filteredColumns, sortedColumns, sortingTypes, maxResult);
		}
	};
}
