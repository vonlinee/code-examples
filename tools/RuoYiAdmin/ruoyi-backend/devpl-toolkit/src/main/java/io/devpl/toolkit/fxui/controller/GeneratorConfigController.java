package io.devpl.toolkit.fxui.controller;

import de.saxsys.mvvmfx.core.FxmlLocation;
import io.devpl.codegen.mbpg.config.builder.CodeGenConfiguration;
import io.devpl.toolkit.fxui.event.UpdateCodeGenConfigEvent;
import io.devpl.toolkit.fxui.framework.mvc.FXControllerBase;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import io.devpl.toolkit.fxui.utils.ConfigHelper;
import io.devpl.toolkit.fxui.framework.Alerts;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 管理GeneratorConfig的Controller
 */
@FxmlLocation(location = "static/fxml/generatorConfigs.fxml")
public class GeneratorConfigController extends FXControllerBase {

    @FXML
    private TableView<GenericConfiguration> configTable;
    @FXML
    private TableColumn<GenericConfiguration, String> nameColumn;
    @FXML
    private TableColumn<GenericConfiguration, String> opsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        // 自定义操作列
        opsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        opsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<GenericConfiguration, String> call(TableColumn<GenericConfiguration, String> param) {
                TableCell<GenericConfiguration, String> tableCell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
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
                                    GenericConfiguration generatorConfig = ConfigHelper.loadGeneratorConfig(item.toString());
                                    post(new UpdateCodeGenConfigEvent(generatorConfig));
                                    getStage(event).close();
                                } catch (Exception e) {
                                    Alerts.error(e.getMessage()).showAndWait();
                                }
                            });
                            btn2.setOnAction(event -> {
                                try {
                                    // 删除配置
                                    ConfigHelper.deleteGeneratorConfig(item.toString());
                                    refreshTableView();
                                } catch (Exception e) {
                                    Alerts.showErrorAlert(e.getMessage());
                                }
                            });
                            setGraphic(hBox);
                        }
                    }
                };
                tableCell.setTextAlignment(TextAlignment.CENTER);
                tableCell.setAlignment(Pos.CENTER);
                return tableCell;
            }
        });
        refreshTableView();
    }

    public void refreshTableView() {
        try {
            List<GenericConfiguration> configs = ConfigHelper.loadGeneratorConfigs();
            configTable.setItems(FXCollections.observableList(configs));
        } catch (Exception e) {
            Alerts.error(e.getMessage()).show();
        }
    }
}
