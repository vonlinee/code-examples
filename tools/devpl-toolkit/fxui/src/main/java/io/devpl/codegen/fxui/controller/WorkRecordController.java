package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.frame.FXController;
import io.devpl.codegen.fxui.model.ClockRecord;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkRecordController extends FXController {

    @FXML
    public AnchorPane root;
    @FXML
    public TableView<ClockRecord> recordTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recordTableView.prefWidthProperty().bindBidirectional(root.prefWidthProperty());
        ObservableList<TableColumn<ClockRecord, ?>> columns = recordTableView.getColumns();
        for (TableColumn<ClockRecord, ?> column : recordTableView.getColumns()) {
            column.prefWidthProperty().bind(recordTableView.widthProperty().divide(columns.size()));
        }
    }
}
