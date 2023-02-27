package io.devpl.tookit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.model.ConnectionInfo;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.fxui.view.navigation.impl.DBTreeView;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主视图
 */
@FxmlLocation(location = "layout/MainView.fxml")
public class MainView extends FxmlView {

    @FXML
    public TextField txfFilter;
    @FXML
    public DBTreeView trvDbNavigation;
    @FXML
    public VBox vboxLeft;
    @FXML
    public Tab tabMbg;
    @FXML
    public TabPane tabpContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trvDbNavigation.prefHeightProperty().bind(vboxLeft.heightProperty().subtract(txfFilter.heightProperty()));

        ConnectionRegistry.getConnectionConfigurations()
                .forEach(connectionInfo -> trvDbNavigation.addConnection(connectionInfo));
    }

    @FXML
    public void showConnectinManagePane(MouseEvent mouseEvent) {
        StageManager.show(ConnectionManageController.class);
    }

    /**
     * 添加新连接，点击每个连接将填充子TreeItem
     *
     * @param connectionInfo 连接信息
     */
    @Subscribe(name = "add-new-connection", threadMode = ThreadMode.BACKGROUND)
    public void addConnection(ConnectionInfo connectionInfo) {
        trvDbNavigation.addConnection(connectionInfo);
    }
}
