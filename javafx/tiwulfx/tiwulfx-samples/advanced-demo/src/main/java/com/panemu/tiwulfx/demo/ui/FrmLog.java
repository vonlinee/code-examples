package com.panemu.tiwulfx.demo.ui;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.LookupFieldController;
import com.panemu.tiwulfx.demo.DaoBase;
import com.panemu.tiwulfx.demo.misc.DataGenerator;
import com.panemu.tiwulfx.demo.misc.EmailValidator;
import com.panemu.tiwulfx.demo.pojo.Insurance;
import com.panemu.tiwulfx.demo.pojo.Person;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FrmLog extends VBox {

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
    private TextArea txtLog;

    private DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);
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

    public FrmLog() {
        FXMLLoader fxmlLoader = new FXMLLoader(FrmPersonTable.class.getResource("FrmLog.fxml"));
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

        // Add button in TableControl's toolbar
        Button button = new Button();
        button.setGraphic(new ImageView(new Image(TableControl.class.getResourceAsStream("/images/chart.png"))));

        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MessageDialog.Answer answer = MessageDialogBuilder.info().message("message.one")
                        .yesOkButtonText("I know")
                        .noButtonText("No, I don't")
                        .buttonType(MessageDialog.ButtonType.YES_NO)
                        .show(FrmLog.this.getScene().getWindow());
                if (answer == MessageDialog.Answer.NO) {
                    MessageDialogBuilder.info().message("Maybe the developer hid it.")
                            .show(FrmLog.this.getScene().getWindow());
                }
            }
        });
        tblPerson.addButton(button);

        // Hide delete button
//		tblPerson.setVisibleComponents(false, TableControl.Component.BUTTON_DELETE);
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
                                .show(FrmLog.this.getScene().getWindow());
                    }
                });
            }
        });

        tblPerson.addContextMenuItem(customMI);

    }

    public void reload() {
        tblPerson.reloadFirstPage();
    }

    private final TableControlBehaviour<Person> controller = new TableControlBehaviour<>() {

        private final DaoBase<Person> daoPerson = new DaoBase<>(Person.class);

        @Override
        public <C> TableData<Person> loadData(int startIndex, List<TableCriteria<C>> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            boolean join = true;
            for (TableCriteria<?> crit : filteredColumns) {
                if (crit.getAttributeName()
                        .equals("insurance") && crit.getOperator() == TableCriteria.Operator.is_null) {
                    join = false;
                    break;
                }
            }
            List<String> lstJoin = new ArrayList<>();
            if (join) {
                lstJoin.add("insurance");
            }
            return (TableData<Person>) daoPerson.fetch(startIndex, filteredColumns, sortedColumns, sortingOrders, maxResult, lstJoin);
        }

        @Override
        public List<Person> insert(List<Person> newRecords) {
            return daoPerson.insert(newRecords);
        }

        @Override
        public List<Person> update(List<Person> records) {
            Runnable runnable = () -> {
                tblPerson.getRecordChangeList().forEach((rc) -> {
                    txtLog.insertText(0, "Property Name: " + rc.getPropertyName() + ", Old Value: " + rc.getOldValue() + ", New Value: " + rc.getNewValue() + ", Timestamp: " + Calendar
                            .getInstance().getTime() + "\n");
                });
            };
            Platform.runLater(runnable);
            List<Person> result = daoPerson.update(records);
            result = daoPerson.initRelationship(records, "insurance");
            return result;
        }

        @Override
        public void postSave(TableControl.OperationMode previousMode) {
            super.postSave(previousMode); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void delete(List<Person> records) {
            daoPerson.delete(records);
        }

        @Override
        public <C> void exportToExcel(String title, int maxResult, TableControl<Person> tblView, List<TableCriteria<C>> lstCriteria) {
            super.exportToExcel("Person Data", maxResult, tblView, lstCriteria);
        }
    };
}
