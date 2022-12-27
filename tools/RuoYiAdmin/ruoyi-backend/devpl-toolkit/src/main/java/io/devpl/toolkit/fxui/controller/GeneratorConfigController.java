package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.fxui.event.UpdateCodeGenConfigEvent;
import io.devpl.toolkit.fxui.framework.mvc.FXControllerBase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import io.devpl.toolkit.fxui.config.CodeGenConfiguration;
import io.devpl.toolkit.fxui.utils.ConfigHelper;
import io.devpl.toolkit.fxui.framework.Alerts;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 管理GeneratorConfig的Controller
 */
public class GeneratorConfigController extends FXControllerBase {

    @FXML
    private TableView<CodeGenConfiguration> configTable;
    @FXML
    private TableColumn<?, String> nameColumn;
    @FXML
    private TableColumn<?, ?> opsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // 自定义操作列
        opsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        opsColumn.setCellFactory(cell -> new TableCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Button btn1 = new Button("应用"), btn2 = new Button("删除");
                    HBox hBox = new HBox(btn1, btn2);
                    hBox.setSpacing(10);
                    btn1.setOnAction(event -> {
                        try {
                            // 应用配置
                            CodeGenConfiguration generatorConfig = ConfigHelper.loadGeneratorConfig(item.toString());
                            post(new UpdateCodeGenConfigEvent(generatorConfig));
                            getStage(event).close();
                        } catch (Exception e) {
                            Alerts.error(e.getMessage()).showAndWait();
                        }
                    });
                    btn2.setOnAction(event -> {
                        try {
                            // 删除配置
                            log.debug("item: {}", item);
                            ConfigHelper.deleteGeneratorConfig(item.toString());
                            refreshTableView();
                        } catch (Exception e) {
                            Alerts.showErrorAlert(e.getMessage());
                        }
                    });
                    setGraphic(hBox);
                }
            }
        });
        refreshTableView();
    }

    public void refreshTableView() {
        try {
            List<CodeGenConfiguration> configs = ConfigHelper.loadGeneratorConfigs();
            configTable.setItems(FXCollections.observableList(configs));
        } catch (Exception e) {
            Alerts.error(e.getMessage()).show();
        }
    }
}
