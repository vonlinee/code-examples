/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo.ui;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.LookupFieldController;
import com.panemu.tiwulfx.demo.DaoBase;
import com.panemu.tiwulfx.demo.TiwulfxDemo;
import com.panemu.tiwulfx.demo.misc.DataGenerator;
import com.panemu.tiwulfx.demo.pojo.Insurance;
import com.panemu.tiwulfx.demo.pojo.Person;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.CheckBoxColumn;
import com.panemu.tiwulfx.table.ComboBoxColumn;
import com.panemu.tiwulfx.table.DateColumn;
import com.panemu.tiwulfx.table.LookupColumn;
import com.panemu.tiwulfx.table.NumberColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableControl.Mode;
import com.panemu.tiwulfx.table.TableController;
import com.panemu.tiwulfx.table.TextColumn;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.layout.VBox;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
public class FrmMasterDetail extends VBox {

	@FXML
	private TableControl<Insurance> tblInsurance;
	@FXML
	private TableControl<Person> tblPerson;
	private DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);

	public FrmMasterDetail() {
		FXMLLoader fxmlLoader = new FXMLLoader(FrmPersonTable.class.getResource("FrmMasterDetail.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		initMasterTable();
		initDetailTable();
		tblInsurance.reloadFirstPage();
	}

	private void initMasterTable() {
		tblInsurance.setRecordClass(Insurance.class);
		tblInsurance.setController(cntlInsurance);
		TextColumn<Insurance> clmCode = new TextColumn<>("code");
		TextColumn<Insurance> clmPackageName = new TextColumn<>("name", 300);
		tblInsurance.addColumn(clmCode, clmPackageName);
		tblInsurance.selectedItemProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if (tblInsurance.getMode() == TableControl.Mode.READ) {
					tblPerson.reloadFirstPage();
				}
			}
		});
	}

	private void initDetailTable() {
		tblPerson.setRecordClass(Person.class);
		tblPerson.setController(cntlPerson);
		tblPerson.setMaxRecord(50);
		tblPerson.getTableView().setTableMenuButtonVisible(true);
		TiwulfxDemo.factory.createEntityManager();
		TextColumn<Person> clmName = new TextColumn<>("name", 150);
		clmName.setEditable(true);
		TextColumn<Person> clmEmail = new TextColumn<>("email", 250);
		DateColumn<Person> clmBirthDate = new DateColumn<>("birthDate");
		clmBirthDate.setRequired(false);
		ComboBoxColumn<Person, String> clmBirthPlace = new ComboBoxColumn<>("birthPlace");
		for (String location : DataGenerator.birthPlaces) {
			clmBirthPlace.addItem(location, location);
		}

		ComboBoxColumn<Person, Character> clmGender = new ComboBoxColumn<>("gender");
		clmGender.addItem("Male", 'm');
		clmGender.addItem("Female", 'f');
		clmGender.setRequired(true);

		CheckBoxColumn<Person> clmAlive = new CheckBoxColumn<>("alive");
		clmAlive.setRequired(false);
		clmAlive.setLabel(TiwulFXUtil.getLiteral("alive.true"), TiwulFXUtil.getLiteral("alive.false"), TiwulFXUtil.getLiteral("alive.null"));

		LookupColumn<Person, Insurance> clmInsurance = new LookupColumn<>("insurance", "code", 75);
		clmInsurance.setLookupController(insuranceLookupController);
		clmInsurance.setText(TiwulFXUtil.getLiteral("insurance"));
		clmInsurance.setSortable(false);//the dao haven't support sorting for join table yet

		NumberColumn<Person, Integer> clmVisit = new NumberColumn<>("visit", Integer.class, 75);

		NumberColumn<Person, Double> clmWeight = new NumberColumn<>("weight", Double.class, 75);

		NumberColumn<Person, Integer> clmVersion = new NumberColumn<>("version", Integer.class, 50);
		clmVersion.setEditable(false);

		tblPerson.addColumn(clmInsurance, clmName, clmEmail, clmBirthPlace, clmBirthDate,
				  clmGender, clmAlive,
				  clmVisit, clmWeight, clmVersion);
		tblPerson.setAgileEditing(true);

	}
	private TableController<Insurance> cntlInsurance = new TableController<Insurance>() {
		@Override
		public TableData loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<SortType> sortingOrders, int maxResult) {
			return daoInsurance.fetch(startIndex, filteredColumns, sortedColumns, sortingOrders, maxResult);
		}

		@Override
		public List<Insurance> insert(List<Insurance> newRecords) {
			return daoInsurance.insert(newRecords);
		}

		@Override
		public List<Insurance> update(List<Insurance> records) {
			return daoInsurance.update(records);
		}

		@Override
		public boolean canDelete(TableControl table) {
			/**
			 * This checking is not perfect. If there are Persons filtered thus not displayed in tblPerson, the delete is not canceled. An error will be displayed along with the stack
			 * trace. The better implementation is to count the children from database and ensure the result is zero.
			 */
			boolean nochildren = tblPerson.getRecords().isEmpty();
			if (!nochildren) {
				MessageDialogBuilder.error().message("Unable to delete Insurance (code "
						  + tblInsurance.getSelectedItem().getCode() + ") because"
						  + "\nthere are Persons refer to it!").show(getScene().getWindow());
			}
			return nochildren;
		}

		@Override
		public void delete(List<Insurance> records) {
			daoInsurance.delete(records);
		}
	};

	private TableController<Person> cntlPerson = new TableController<Person>() {
		private DaoBase<Person> daoPerson = new DaoBase<>(Person.class);

		@Override
		public TableData loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
			/**
			 * We put the criteria for parent table here instead of setting criteria to clmInsurance (by calling clmInsurance.setTableCriteria()). If the criteria is set directly to
			 * the column, user can remove it. In this case, we don't want user to be able to remove this criteria which means breaking the master-detail presentation.
			 */
			Insurance selectedInsurance = tblInsurance.getSelectedItem();
			TableCriteria<Insurance> criteria = new TableCriteria<>("insurance", TableCriteria.Operator.eq, selectedInsurance);
			filteredColumns.add(criteria);

			TableData result = daoPerson.fetch(startIndex, filteredColumns, sortedColumns, sortingOrders, maxResult, Arrays.asList("insurance"));
			return result;
		}

		@Override
		public Person preInsert(Person newRecord) {
			Insurance selectedInsurance = tblInsurance.getSelectedItem();
			newRecord.setInsurance(selectedInsurance);
			return newRecord;
		}

		@Override
		public List<Person> insert(List<Person> newRecords) {
			return daoPerson.insert(newRecords);
		}

		@Override
		public List<Person> update(List<Person> records) {
			return daoPerson.update(records);
		}

		@Override
		public void delete(List<Person> records) {
			daoPerson.delete(records);
		}

		@Override
		public void postSave(Mode previousMode) {
			/**
			 * If value for "insurance changed, reloading tblPerson will exclude all rows that the insurance is not currently selected insurance.
			 */
			tblPerson.reload();
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
		protected String getWindowTitle() {
			return "Find Insurance";
		}

		@Override
		protected void initCallback(VBox container, TableControl<Insurance> table) {
			container.setPrefWidth(500);
			table.getTableView().getColumns().get(2).setPrefWidth(300);
		}

		@Override
		protected TableData loadData(int startIndex, List filteredColumns, List sortedColumns, List sortingTypes, int maxResult) {
			return daoInsurance.fetch(startIndex, filteredColumns, sortedColumns, sortingTypes, maxResult);
		}
	};
}
