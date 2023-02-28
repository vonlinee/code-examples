package io.devpl.tookit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.event.DeleteConnEvent;
import io.devpl.tookit.fxui.model.ConnectionInfo;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.utils.AppConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 数据库连接信息管理控制器
 */
@FxmlLocation(location = "layout/connection_manage.fxml", title = "数据库连接管理")
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
    @FXML
    public TableColumn<ConnectionInfo, String> tblcConnectionName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblvConnectionList.setRowFactory(param -> {
            final TableRow<ConnectionInfo> row = new TableRow<>();
            row.setAlignment(Pos.CENTER);
            row.setTextAlignment(TextAlignment.CENTER);
            return row;
        });
        tblvConnectionList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcDbType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverInfo().name()));
        tblcHostname.setCellValueFactory(new PropertyValueFactory<>("host"));
        tblcPort.setCellValueFactory(new PropertyValueFactory<>("port"));
        tblcDatabaseName.setCellValueFactory(new PropertyValueFactory<>("dbName"));
        tblcConnectionName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getConnectionName()));
        tblcProtocol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()
                .getDriverInfo()
                .getSubProtocol()));
        fillConnectionInfo();
    }

    @FXML
    public void btnNewConnection(ActionEvent actionEvent) {
        StageManager.show(NewConnectionController.class);
    }

    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addNewConnectionInfo(ConnectionInfo connectionInfo) {
        tblvConnectionList.getItems().add(connectionInfo);
    }

    public void fillConnectionInfo() {
        tblvConnectionList.getItems().addAll(ConnectionRegistry.getConnectionConfigurations());
    }

    @FXML
    public void deleteConnection(ActionEvent actionEvent) {
        ObservableList<ConnectionInfo> selectedItems = tblvConnectionList.getSelectionModel().getSelectedItems();
        List<ConnectionInfo> list = new ArrayList<>(selectedItems);
        try {
            if (AppConfig.deleteConnectionById(list) == list.size()) {
                tblvConnectionList.getItems().removeAll(list);
            }
        } catch (Exception exception) {
            Alerts.exception("删除连接失败", exception).show();
            return;
        }
        DeleteConnEvent event = new DeleteConnEvent();
        event.setConnectionNames(list.stream().map(ConnectionInfo::getConnectionName).collect(Collectors.toList()));
        publish(event);
    }
}
