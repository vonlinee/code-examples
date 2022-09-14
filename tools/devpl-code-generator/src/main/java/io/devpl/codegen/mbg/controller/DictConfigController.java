package io.devpl.codegen.mbg.controller;

import io.devpl.codegen.mbg.model.SysDictData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 字典配置管理控制器
 */
public class DictConfigController extends FXControllerBase {

    @FXML
    public TableView<SysDictData> dictConfigTable;
    @FXML
    public TableColumn<SysDictData, String> nameColumn;
    @FXML
    public TableColumn<SysDictData, String> valueColumn;
    @FXML
    public TableColumn<SysDictData, String> descColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        ObservableList<SysDictData> data = FXCollections.observableArrayList(
                new SysDictData("K1", "V1", "描述信息"),
                new SysDictData("K2", "V2", "描述信息"));
        dictConfigTable.setItems(data);
    }
}
