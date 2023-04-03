package io.devpl.tookit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.event.DeleteConnEvent;
import io.devpl.tookit.fxui.model.ConnectionInfo;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.fxui.view.navigation.impl.DBTreeView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * 主视图
 */
@FxmlLocation(location = "layout/MainView.fxml")
public class MainView extends FxmlView {

    @FXML
    public Tab tabMbg;
    @FXML
    public TabPane tabpContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ConnectionRegistry.getConnectionConfigurations()
        //         .forEach(connectionInfo -> trvDbNavigation.addConnection(connectionInfo));
    }

    @FXML
    public void showConnectionManagePane(MouseEvent mouseEvent) {
        StageManager.show(ConnectionManageController.class);
    }

    /**
     * 添加新连接，点击每个连接将填充子TreeItem
     *
     * @param connectionInfo 连接信息
     */
    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addConnection(ConnectionInfo connectionInfo) {
        // trvDbNavigation.addConnection(connectionInfo);
    }

    /**
     * 删除数据库连接
     *
     * @param event
     */
    @Subscribe
    public void removeConnection(DeleteConnEvent event) {
        // ObservableList<TreeItem<String>> children = trvDbNavigation.getRoot().getChildren();
        // Iterator<TreeItem<String>> iterator = children.iterator();
        // for (String connectionName : event.getConnectionNames()) {
        //     while (iterator.hasNext()) {
        //         TreeItem<String> next = iterator.next();
        //         if (next.getValue().equals(connectionName)) {
        //             iterator.remove();
        //         }
        //     }
        // }
    }
}
