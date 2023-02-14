package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.TableCodeGenConfig;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import io.devpl.toolkit.fxui.utils.EventUtils;
import io.devpl.toolkit.fxui.view.navigation.ConnectionTreeItem;
import io.devpl.toolkit.fxui.view.navigation.TableTreeItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 数据库导航控制器
 */
@FxmlLocation(location = "layout/database_navigation.fxml")
public class DatabaseNavigationController extends FxmlView {

    @FXML
    public TextField txfFilter;
    @FXML
    public TreeView<String> trvDbConnection; // 数据库连接TreeView
    @FXML
    public VBox vboxRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trvDbConnection.prefHeightProperty().bind(vboxRoot.heightProperty().subtract(txfFilter.heightProperty()));
        trvDbConnection.setCellFactory(param -> {
            TextFieldTreeCell<String> treeCell = new TextFieldTreeCell<>();
            treeCell.setGraphicTextGap(5);
            treeCell.setOnMouseClicked(event -> {
                if (EventUtils.isPrimaryButtonDoubleClicked(event)) {
                    TreeItem<String> treeItem = treeCell.getTreeItem();
                    // 双击连接，获取所有数据库
                    if (treeItem instanceof ConnectionTreeItem) {
                        ConnectionTreeItem connectionTreeItem = (ConnectionTreeItem) treeItem;
                        connectionTreeItem.fillChildren();
                    } else if (treeItem instanceof TableTreeItem) {
                        // 添加表到选择的表中
                        TreeItem<String> parent = treeItem.getParent();
                        ConnectionTreeItem connectionInfoItem = (ConnectionTreeItem) parent.getParent();
                        TableCodeGenConfig table = new TableCodeGenConfig();
                        table.setDatabaseName(parent.getValue());
                        table.setTableName(treeItem.getValue());
                        table.setConnectionName(connectionInfoItem.getValue());
                        publish(table);
                    }
                    treeItem.setExpanded(true);
                }
            });
            return treeCell;
        });
        for (ConnectionConfig connectionConfiguration : ConnectionRegistry.getConnectionConfigurations()) {
            addConnection(connectionConfiguration);
        }

        // TODO 测试的时候
        ObservableList<TreeItem<String>> children = trvDbConnection.getRoot().getChildren();
        for (TreeItem<String> child : children) {
            if (child instanceof ConnectionTreeItem) {
                ConnectionTreeItem item = (ConnectionTreeItem) child;
                item.fillChildren();
                item.setExpanded(true);
            }
        }
    }

    /**
     * 添加新连接，点击每个连接将填充子TreeItem
     * @param connectionInfo 连接信息
     */
    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addConnection(ConnectionConfig connectionInfo) {
        ConnectionTreeItem treeItem = new ConnectionTreeItem(connectionInfo);
        treeItem.setGraphic(FontIcon.of(FontAwesomeRegular.FOLDER));
        treeItem.setValue(connectionInfo.getConnectionName());
        trvDbConnection.getRoot().getChildren().add(treeItem);
    }
}
