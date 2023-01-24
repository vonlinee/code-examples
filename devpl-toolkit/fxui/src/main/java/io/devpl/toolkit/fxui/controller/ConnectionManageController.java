package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.dao.ConnectionInfoRepository;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 数据库连接信息管理控制器
 */
@FxmlLocation(location = "static/fxml/connection_manage.fxml")
public class ConnectionManageController extends FxmlView {

    @FXML
    public TableColumn<ConnectionInfo, String> tblcDbType;
    @FXML
    public TableColumn<ConnectionInfo, String> tblcProtocol;
    @FXML
    public TableColumn<ConnectionInfo, String> tblcHostname;
    @FXML
    public TableColumn<ConnectionInfo, String> tblcPort;
    @FXML
    public TableColumn<ConnectionInfo, String> tblcDatabaseName;
    @FXML
    public TableView<ConnectionInfo> tblvConnectionList;

    ConnectionInfoRepository repository = new ConnectionInfoRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblvConnectionList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcDbType.setCellValueFactory(param -> param.getValue().dbTypeProperty());
        tblcHostname.setCellValueFactory(param -> param.getValue().hostProperty());
        tblcPort.setCellValueFactory(param -> param.getValue().portProperty());
        tblcDatabaseName.setCellValueFactory(param -> param.getValue().dbNameProperty());
        tblvConnectionList.getItems().addAll(repository.selectList());
    }

    @FXML
    public void btnAddOne(ActionEvent actionEvent) {
        StageHelper.show("", NewConnectionController.class);
    }

    /**
     * 刷新表格数据
     */
    private void refreshTableView() {
        tblvConnectionList.getItems().clear();
        tblvConnectionList.getItems().addAll(repository.selectList());
    }

    @FXML
    public void btnClearAll(ActionEvent actionEvent) {
        tblvConnectionList.getItems().clear();
    }

    @FXML
    public void btnRefresh(ActionEvent actionEvent) {
        refreshTableView();
    }

    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addNewConnectionInfo(ConnectionInfo connectionInfo) {
        tblvConnectionList.getItems().add(connectionInfo);
    }
}
