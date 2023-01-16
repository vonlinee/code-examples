package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.Icon;
import io.devpl.toolkit.fxui.view.navigation.ConnectionTreeItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 数据库导航控制器
 */
@FxmlLocation(location = "static/fxml/database_navigation.fxml")
public class DatabaseNavigationController extends FxmlView {

    @FXML
    public TextField txfFilter;
    @FXML
    public TreeView<String> trvDbConnection;
    @FXML
    public VBox vboxRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trvDbConnection.prefHeightProperty().bind(vboxRoot.heightProperty().subtract(txfFilter.heightProperty()));
        trvDbConnection.setCellFactory(param -> {
            TextFieldTreeCell<String> treeCell = new TextFieldTreeCell<>();
            treeCell.setGraphicTextGap(5);
            return treeCell;
        });
    }

    /**
     * 添加新连接
     * @param connectionInfo 连接信息
     */
    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addConnection(ConnectionInfo connectionInfo) throws SQLException {
        ConnectionTreeItem treeItem = new ConnectionTreeItem(connectionInfo);
        treeItem.setGraphic(Icon.FOLDER);
        treeItem.connect();
        trvDbConnection.getRoot().getChildren().add(treeItem);
    }
}
