package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.common.model.SysDictData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 字典配置管理控制器
 */
public class DictConfigController extends FXControllerBase {

    @FXML
    public TableView<SysDictData> dictConfigTable;
    @FXML
    public TableColumn<SysDictData, String> tbcName;
    @FXML
    public TableColumn<SysDictData, String> tbcCode;
    @FXML
    public TableColumn<SysDictData, String> tbcValue;
    @FXML
    public TableColumn<SysDictData, String> tbcDesc;
    @FXML
    public TableColumn<SysDictData, CheckBox> tbcEnabled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tbcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tbcDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbcEnabled.setCellValueFactory(param -> {
            ObjectProperty<CheckBox> isEnableProperty = new SimpleObjectProperty<>();
            final CheckBox checkBox = new CheckBox();
            checkBox.setSelected(param.getValue().isEnabled());
            isEnableProperty.set(checkBox);
            return isEnableProperty;
        });
        final SysDictData sysDictData = new SysDictData();
        dictConfigTable.setItems(FXCollections.observableList(List.of(sysDictData)));
    }
}
