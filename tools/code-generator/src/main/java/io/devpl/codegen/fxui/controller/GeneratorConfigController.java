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

import io.devpl.codegen.fxui.model.CodeGenConfiguration;
import io.devpl.codegen.common.utils.ConfigHelper;
import io.devpl.codegen.fxui.utils.AlertDialog;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GeneratorConfigController extends FXController {

    private static final Logger _LOG = LoggerFactory.getLogger(GeneratorConfigController.class);

    @FXML
    private TableView<CodeGenConfiguration> configTable;
    @FXML
    private TableColumn<CodeGenConfiguration, String> nameColumn;
    @FXML
    private TableColumn opsColumn;

    private MainController mainUIController;

    private GeneratorConfigController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // 自定义操作列
        opsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        opsColumn.setCellFactory(cell -> {
            return new TableCell() {
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
                                AlertDialog.showError(e.getMessage());
                            }
                        });
                        btn2.setOnAction(event -> {
                            try {
                                // 删除配置
                                _LOG.debug("item: {}", item);
                                ConfigHelper.deleteGeneratorConfig(item.toString());
                                refreshTableView();
                            } catch (Exception e) {
                                AlertDialog.showError(e.getMessage());
                            }
                        });
                        setGraphic(hBox);
                    }
                }
            };
        });
        refreshTableView();
    }

    public void refreshTableView() {
        try {
            List<CodeGenConfiguration> configs = ConfigHelper.loadGeneratorConfigs();
            configTable.setItems(FXCollections.observableList(configs));
        } catch (Exception e) {
            AlertDialog.showError(e.getMessage());
        }
    }

    void setMainUIController(MainController mainUIController) {
        this.mainUIController = mainUIController;
    }
}
