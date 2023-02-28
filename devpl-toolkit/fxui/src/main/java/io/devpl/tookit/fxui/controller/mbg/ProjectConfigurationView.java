package io.devpl.tookit.fxui.controller.mbg;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.model.ProjectConfiguration;
import io.devpl.tookit.utils.AppConfig;
import io.devpl.tookit.utils.json.JSONUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 项目配置表
 */
@FxmlLocation(location = "layout/mbg/ProjectConfigurationView.fxml")
public class ProjectConfigurationView extends FxmlView {

    @FXML
    public TableView<ProjectConfiguration> tbvConfig;
    @FXML
    public TableColumn<ProjectConfiguration, String> tblcName;
    @FXML
    public TableColumn<ProjectConfiguration, String> tblcValue;
    @FXML
    public Button btnApply;
    @FXML
    public BorderPane root;
    @FXML
    public Button btnRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblcName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        tblcValue.setCellValueFactory(param -> new SimpleStringProperty(JSONUtils.toJSONString(param.getValue())));
        Event.fireEvent(btnRefresh, new ActionEvent());
    }

    @FXML
    public void applyConfig(ActionEvent actionEvent) {
        ProjectConfiguration selectedItem = tbvConfig.getSelectionModel().getSelectedItem();
        publish("LoadConfig", selectedItem);
        tbvConfig.getSelectionModel().clearSelection();
        getStage(tbvConfig).close();
    }

    @FXML
    public void refreshConfig(ActionEvent actionEvent) {
        tbvConfig.getItems().clear();
        AppConfig.listProjectConfigurations().forEach(item -> tbvConfig.getItems().add(item));
    }
}
