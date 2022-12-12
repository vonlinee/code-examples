package io.devpl.codegen.fxui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.devpl.codegen.fxui.config.CodeGenConfiguration;
import io.devpl.codegen.fxui.utils.ConfigHelper;
import io.devpl.codegen.fxui.framework.Alerts;

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
    private TableColumn nameColumn;
    @FXML
    private TableColumn opsColumn;

    private MainUIController mainUIController;

    private GeneratorConfigController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
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
                    Button btn1 = new Button("应用");
                    Button btn2 = new Button("删除");
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);
                    hBox.getChildren().add(btn1);
                    hBox.getChildren().add(btn2);
                    btn1.setOnAction(event -> {
                        try {
                            // 应用配置
                            CodeGenConfiguration generatorConfig = ConfigHelper.loadGeneratorConfig(item.toString());
                            mainUIController.setGeneratorConfigIntoUI(generatorConfig);
                            controller.closeDialogStage();
                        } catch (Exception e) {
                            Alerts.showErrorAlert(e.getMessage());
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
            Alerts.showErrorAlert(e.getMessage());
        }
    }

    void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }
}
