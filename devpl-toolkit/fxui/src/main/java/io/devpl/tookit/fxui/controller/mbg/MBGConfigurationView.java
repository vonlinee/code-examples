package io.devpl.tookit.fxui.controller.mbg;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.beans.ValueUpdateListener;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.model.ProjectConfiguration;
import io.devpl.tookit.fxui.model.TableCodeGeneration;
import io.devpl.tookit.utils.AppConfig;
import io.devpl.tookit.utils.StringUtils;
import io.devpl.tookit.utils.Validator;
import io.devpl.tookit.utils.fx.FileChooserDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import org.greenrobot.eventbus.Subscribe;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
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
    @FXML
    public Button btnSaveConfig;
    @FXML
    public Button btnLoadConfig;
    @FXML
    public ChoiceBox<String> chobProjectLayout;

    /**
     * 项目配置项
     */
    private final ProjectConfiguration projectConfig = new ProjectConfiguration();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValueUpdateListener.bind(projectFolderField.textProperty(), projectConfig, ProjectConfiguration::setProjectRootFolder);
        ValueUpdateListener.bind(modelTargetPackage.textProperty(), projectConfig, ProjectConfiguration::setEntityPackageName);
        ValueUpdateListener.bind(modelTargetProject.textProperty(), projectConfig, ProjectConfiguration::setEntityPackageFolder);
        ValueUpdateListener.bind(txfParentPackageName.textProperty(), projectConfig, ProjectConfiguration::setParentPackage);
        ValueUpdateListener.bind(txfMapperPackageName.textProperty(), projectConfig, ProjectConfiguration::setMapperPackageName);
        ValueUpdateListener.bind(daoTargetProject.textProperty(), projectConfig, ProjectConfiguration::setMapperFolder);
        ValueUpdateListener.bind(mapperTargetPackage.textProperty(), projectConfig, ProjectConfiguration::setMapperXmlPackage);
        ValueUpdateListener.bind(mappingTargetProject.textProperty(), projectConfig, ProjectConfiguration::setMapperXmlFolder);

        chobProjectLayout.getItems().addAll("MAVEN");

        chobProjectLayout.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("MAVEN".equals(newValue)) {
                modelTargetProject.setText("src/main/java");
                daoTargetProject.setText("src/main/java");
                mappingTargetProject.setText("src/main/resources");
            }
        });
    }

    /**
     * 校验配置项
     *
     * @param projectConfig 项目配置
     */
    private String validateConfig(ProjectConfiguration projectConfig) {
        return Validator.target(projectConfig)
                .hasText(ProjectConfiguration::getProjectRootFolder, "项目根目录为空")
                .hasText(ProjectConfiguration::getMapperPackageName, "Mapper接口包名为空")
                .hasText(ProjectConfiguration::getEntityPackageName, "实体类包名为空")
                .hasText(ProjectConfiguration::getEntityPackageFolder, "实体类所在目录为空")
                .hasText(ProjectConfiguration::getMapperXmlPackage, "映射XML文件包名为空")
                .getErrorMessages();
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

    /**
     * 保存配置
     *
     * @param actionEvent 事件
     */
    @FXML
    public void saveCodeGenConfig(ActionEvent actionEvent) {
        String errMsgs = validateConfig(this.projectConfig);
        if (StringUtils.hasText(errMsgs)) {
            Alerts.error(errMsgs).show();
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("保存代码生成配置");
        dialog.setContentText("输入配置名称:");

        Optional<String> resultOptional = dialog.showAndWait();
        if (resultOptional.isPresent()) {
            this.projectConfig.setName(resultOptional.get());
        } else {
            Alerts.error("配置名称不能为空");
        }
        AppConfig.saveProjectConfiguration(this.projectConfig);
    }

    @Subscribe(name = "LoadConfig")
    public void loadConfig(ProjectConfiguration projectConfig) {
        projectFolderField.setText(projectConfig.getProjectRootFolder());
        modelTargetPackage.setText(projectConfig.getEntityPackageName());
        modelTargetProject.setText(projectConfig.getEntityPackageFolder());
        txfParentPackageName.setText(projectConfig.getParentPackage());
        txfMapperPackageName.setText(projectConfig.getMapperPackageName());
        daoTargetProject.setText(projectConfig.getMapperFolder());
        mapperTargetPackage.setText(projectConfig.getMapperXmlPackage());
        mappingTargetProject.setText(projectConfig.getMapperXmlFolder());
    }

    @FXML
    public void openProjectConfiguration(ActionEvent actionEvent) {
        StageManager.show(ProjectConfigurationView.class);
    }

    @Subscribe(name = "PrepareGeneration")
    void startGenerate(Map<String, TableCodeGeneration> tableCodeGenerationMap) {
        publish("DoGeneration", this.projectConfig);
    }
}
