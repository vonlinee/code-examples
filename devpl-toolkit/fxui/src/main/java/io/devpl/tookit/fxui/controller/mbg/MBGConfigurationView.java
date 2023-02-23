package io.devpl.tookit.fxui.controller.mbg;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.beans.ValueUpdateListener;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.model.CodeGenConfiguration;
import io.devpl.tookit.utils.Validator;
import io.devpl.tookit.utils.fx.FileChooserDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * MyBatis Generator配置
 */
@FxmlLocation(location = "layout/mbg/MBGConfigurationView.fxml")
public class MBGConfigurationView extends FxmlView {

    @FXML
    public TextField txfParentPackageName;
    @FXML
    public TextField modelTargetPackage;
    @FXML
    public TextField mapperTargetPackage;
    @FXML
    public TextField txfMapperPackageName;  // DAO接口包名
    @FXML
    public TextField modelTargetProject; // 实体类存放目录
    @FXML
    public TextField mappingTargetProject; // 映射XML文件包名存放目录
    @FXML
    public TextField daoTargetProject;
    @FXML
    public TextField projectFolderField;

    /**
     * 通用配置项
     */
    private final CodeGenConfiguration codeGenConfig = new CodeGenConfiguration();

    @FXML
    public Button btnSaveConfig;
    @FXML
    public Button btnLoadConfig;
    @FXML
    public ChoiceBox<String> chobProjectLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCodeGenConfiguration(codeGenConfig);

        chobProjectLayout.getItems().addAll("MAVEN");

        chobProjectLayout.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ("MAVEN".equals(newValue)) {
                    modelTargetProject.setText("src/main/java");
                    daoTargetProject.setText("src/main/java");
                    mappingTargetProject.setText("src/main/resources");
                }
            }
        });
    }

    private void validateConfig(CodeGenConfiguration codeGenConfig) {
        final String errorMessages = Validator.target(codeGenConfig)
                .hasText(CodeGenConfiguration::getDaoPackage, "Mapper接口包名为空")
                .hasText(CodeGenConfiguration::getParentPackage, "父包名为空")
                .hasText(CodeGenConfiguration::getMappingXMLPackage, "映射XML文件包名为空")
                .getErrorMessages();
        Alerts.error(errorMessages).showAndWait().ifPresent(btn -> {

        });
    }

    /**
     * 绑定数据
     *
     * @param generatorConfig 代码生成配置
     */
    public void bindCodeGenConfiguration(CodeGenConfiguration generatorConfig) {
        ValueUpdateListener.bind(projectFolderField.textProperty(), generatorConfig, CodeGenConfiguration::setProjectFolder);
        ValueUpdateListener.bind(modelTargetPackage.textProperty(), generatorConfig, CodeGenConfiguration::setModelPackage);
        ValueUpdateListener.bind(modelTargetProject.textProperty(), generatorConfig, CodeGenConfiguration::setModelPackageTargetFolder);
        ValueUpdateListener.bind(txfParentPackageName.textProperty(), generatorConfig, CodeGenConfiguration::setParentPackage);
        ValueUpdateListener.bind(txfMapperPackageName.textProperty(), generatorConfig, CodeGenConfiguration::setDaoPackage);
        ValueUpdateListener.bind(daoTargetProject.textProperty(), generatorConfig, CodeGenConfiguration::setDaoTargetFolder);
        ValueUpdateListener.bind(mapperTargetPackage.textProperty(), generatorConfig, CodeGenConfiguration::setMappingXMLPackage);
        ValueUpdateListener.bind(mappingTargetProject.textProperty(), generatorConfig, CodeGenConfiguration::setMappingXMLTargetFolder);
    }

    /**
     * 选择项目文件夹
     *
     * @param event 事件
     */
    @FXML
    public void chooseProjectFolder(ActionEvent event) {
        FileChooserDialog.showDirectoryDialog(getStage(event))
                .ifPresent(file -> projectFolderField.setText(file.getAbsolutePath()));
    }

    @FXML
    public void saveCodeGenConfig(ActionEvent actionEvent) {

        validateConfig(this.codeGenConfig);
    }
}
