package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 数据库连接信息管理控制器
 */
@FxmlLocation(location = "static/fxml/connection_manage.fxml")
public class ConnectionManageController extends FxmlView {

    @FXML
    public TableColumn<ConnectionConfig, String> tblcDbType;
    @FXML
    public TableColumn<ConnectionConfig, String> tblcProtocol;
    @FXML
    public TableColumn<ConnectionConfig, String> tblcHostname;
    @FXML
    public TableColumn<ConnectionConfig, String> tblcPort;
    @FXML
    public TableColumn<ConnectionConfig, String> tblcDatabaseName;
    @FXML
    public TableView<ConnectionConfig> tblvConnectionList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblvConnectionList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcDbType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverInfo().name()));
        tblcHostname.setCellValueFactory(new PropertyValueFactory<>("host"));
        tblcPort.setCellValueFactory(new PropertyValueFactory<>("host"));
        tblcDatabaseName.setCellValueFactory(new PropertyValueFactory<>("dbName"));
    }

    @FXML
    public void btnAddOne(ActionEvent actionEvent) {
        StageHelper.show("", NewConnectionController.class);
    }

    @FXML
    public void btnClearAll(ActionEvent actionEvent) {
        tblvConnectionList.getItems().clear();
    }

    @FXML
    public void btnRefresh(ActionEvent actionEvent) {

    }

    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addNewConnectionInfo(ConnectionConfig connectionInfo) {
        tblvConnectionList.getItems().add(connectionInfo);
    }
}
