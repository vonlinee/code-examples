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
import com.panemu.tiwulfx.demo.misc.DataGenerator;
import com.panemu.tiwulfx.demo.misc.EmailValidator;
import com.panemu.tiwulfx.demo.misc.ProgressBarColumn;
import com.panemu.tiwulfx.demo.pojo.Insurance;
import com.panemu.tiwulfx.demo.pojo.Person;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amrullah <amrullah@panemu.com>
 */
public class FrmOddEditablePersonTable extends VBox {

    @FXML
    protected TableControl<Person> tblPerson;
    @FXML
    private TypeAheadColumn<Person, String> clmBirthPlace;
    @FXML
    private ComboBoxColumn<Person, Character> clmGender;
    @FXML
    private LookupColumn<Person, Insurance> clmInsurance;
    @FXML
    private TextColumn<Person> clmEmail;
    @FXML
    private NumberColumn<Person, Integer> clmVisit;
    @FXML
    private NumberColumn<Person, Integer> clmInsuranceId;
    @FXML
    private NumberColumn<Person, Integer> clmVersion;
    @FXML
    private TickColumn<Person> clmTick;
    @FXML
    private TextArea txtInformation;

    private DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);

    public FrmOddEditablePersonTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(FrmOddEditablePersonTable.class.getResource("FrmPersonTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setResources(TiwulFXUtil.getLiteralBundle());
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        init(true);
        tblPerson.reloadFirstPage();
    }

    protected void init(boolean showTickColumn) {
        tblPerson.setRecordClass(Person.class);
        tblPerson.setBehaviour(controller);
        tblPerson.setMaxRecord(50);

        for (String location : DataGenerator.birthPlaces) {
            clmBirthPlace.addItem(location, location);
        }

        clmGender.addItem("Male", 'm');
        clmGender.addItem("Female", 'f');

        clmInsuranceId.setNumberType(Integer.class);
        clmInsurance.setLookupController(insuranceLookupController);
        clmEmail.addValidator(new EmailValidator());
        clmVisit.setNumberType(Integer.class);
        clmVersion.setNumberType(Integer.class);
        /**
         * Custom column. Not included in TiwulFX library
         */

        //TODO put back the progress bar. there is NPE
        ProgressBarColumn<Person, Integer> clmProgress = new ProgressBarColumn<>("visit");
        clmProgress.setEditable(false);
        clmProgress.setMax(5000);
        tblPerson.getColumns().add(9, clmProgress);
        MenuItem ctxMenu = new MenuItem("Get Ticked");
        ctxMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                MessageDialogBuilder.info().message("Ticked record count: " + clmTick.getTickedRecords().size())
                        .show(getScene().getWindow());
            }
        });
        tblPerson.addContextMenuItem(ctxMenu);

        clmVisit.addEditCommitListener(new EditCommitListener<Person, Integer>() {

            @Override
            public void editCommited(CustomTableColumn<Person, Integer> column, Person record, Integer oldValue, Integer newValue) {
                /**
                 * This will update the progress bar
                 */
                tblPerson.refresh(record);
            }
        });

        txtInformation.setText("Only odd rows are editable in this table. Override TableController.isRecordEditable(Record) to specify which rows are editable.");
    }

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

    private TableBehaviourBase<Person> controller = new TableBehaviourBase<Person>() {

        private DaoBase<Person> daoPerson = new DaoBase<>(Person.class);

        @Override
        public <C> TableData loadData(int startIndex, List<TableCriteria<C>> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            boolean join = true;
//        for (TableCriteria crit : filteredColumns) {
//            if (crit.getAttributeName().equals("insurance") && crit.getOperator() == Operator.is_null) {
//                join = false;
//                break;
//            }
//        }
            List<String> lstJoin = new ArrayList<>();
            if (join) {
                lstJoin.add("insurance");
            }
            TableData<Person> result = daoPerson.fetch(startIndex, filteredColumns, sortedColumns, sortingOrders, maxResult, lstJoin);

//		  for (int i = 0;i < 3; i++) {
//			  try {
//				  Thread.sleep(1000);
//			  } catch (InterruptedException ex) {
//				  Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//			  }
//		  }
//		  
            return result;
        }

        @Override
        public List<Person> insert(List<Person> newRecords) {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
            return daoPerson.insert(newRecords);
        }

        @Override
        public List<Person> update(List<Person> records) {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<Person> result = daoPerson.update(records);
            result = daoPerson.initRelationship(records, "insurance");
//		throw new RuntimeException("testing error from background thread");
            return result;
        }

        @Override
        public void delete(List<Person> records) {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
            daoPerson.delete(records);
        }

        @Override
        public <C> void exportToExcel(String title, int maxResult, TableControl<Person> tblView, List<TableCriteria<C>> lstCriteria) {
            super.exportToExcel("Person Data", maxResult, tblView, lstCriteria);
        }

        @Override
        public boolean isRecordEditable(Person item) {
			return item.getId() % 2 != 0;
		}
    };

}
