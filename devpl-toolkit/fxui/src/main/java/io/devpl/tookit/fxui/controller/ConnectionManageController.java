package io.devpl.tookit.fxui.controller;

import io.fxtras.Alerts;
import io.fxtras.mvc.FxmlLocation;
import io.fxtras.mvc.FxmlView;
import io.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.event.DeleteConnEvent;
import io.devpl.tookit.fxui.model.ConnectionConfig;
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
    @FXML
    public TableColumn<ConnectionConfig, String> tblcConnectionName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblvConnectionList.setRowFactory(param -> {
            final TableRow<ConnectionConfig> row = new TableRow<>();
            row.setAlignment(Pos.CENTER);
            row.setTextAlignment(TextAlignment.CENTER);
            return row;
        });
        tblvConnectionList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcDbType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriver().name()));
        tblcHostname.setCellValueFactory(new PropertyValueFactory<>("host"));
        tblcPort.setCellValueFactory(new PropertyValueFactory<>("port"));
        tblcDatabaseName.setCellValueFactory(new PropertyValueFactory<>("dbName"));
        tblcConnectionName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getConnectionName()));
        tblcProtocol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()
                .getDriver()
                .getSubProtocol()));
        fillConnectionInfo();
    }

    @FXML
    public void btnNewConnection(ActionEvent actionEvent) {
        StageManager.show(NewConnectionController.class);
    }

    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addNewConnectionInfo(ConnectionConfig connectionInfo) {
        tblvConnectionList.getItems().add(connectionInfo);
    }

    public void fillConnectionInfo() {
        tblvConnectionList.getItems().addAll(ConnectionRegistry.getConnectionConfigurations());
    }

    @FXML
    public void deleteConnection(ActionEvent actionEvent) {
        ObservableList<ConnectionConfig> selectedItems = tblvConnectionList.getSelectionModel().getSelectedItems();
        List<ConnectionConfig> list = new ArrayList<>(selectedItems);
        try {
            if (AppConfig.deleteConnectionById(list) == list.size()) {
                tblvConnectionList.getItems().removeAll(list);
            }
        } catch (Exception exception) {
            Alerts.exception("删除连接失败", exception).show();
            return;
        }
        DeleteConnEvent event = new DeleteConnEvent();
        event.setConnectionNames(list.stream().map(ConnectionConfig::getConnectionName).collect(Collectors.toList()));
        publish(event);
    }
}
