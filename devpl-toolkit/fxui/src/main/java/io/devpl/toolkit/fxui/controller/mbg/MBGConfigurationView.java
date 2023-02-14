package io.devpl.toolkit.fxui.controller.mbg;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import io.devpl.toolkit.fxui.utils.FileChooserDialog;
import io.devpl.toolkit.fxui.utils.FileUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
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
    public TextField mappingTargetProject;
    @FXML
    public TextField daoTargetProject;
    @FXML
    public TextField projectFolderField;

    /**
     * 通用配置项
     */
    private final GenericConfiguration codeGenConfig = new GenericConfiguration();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCodeGenConfiguration(codeGenConfig);
    }

    @FXML
    public void fillDefaultCodeGenConfig(ActionEvent actionEvent) {
        projectFolderField.setText("D:/Temp/");
        daoTargetProject.setText("src/main/java");
        mapperTargetPackage.setText("mapping");
        txfMapperPackageName.setText("mapper");
        mappingTargetProject.setText("src/main/resources");
        modelTargetPackage.setText("entity");
    }

    /**
     * 绑定数据
     *
     * @param generatorConfig 代码生成配置
     */
    public void bindCodeGenConfiguration(GenericConfiguration generatorConfig) {
        projectFolderField.textProperty().bindBidirectional(generatorConfig.projectFolderProperty());
        modelTargetPackage.textProperty().bindBidirectional(generatorConfig.modelPackageProperty());
        modelTargetProject.textProperty().bindBidirectional(generatorConfig.modelPackageTargetFolderProperty());
        txfParentPackageName.textProperty().bindBidirectional(generatorConfig.parentPackageProperty());
        txfMapperPackageName.textProperty().bindBidirectional(generatorConfig.daoPackageProperty());
        daoTargetProject.textProperty().bindBidirectional(generatorConfig.daoTargetFolderProperty());
        mapperTargetPackage.textProperty().bindBidirectional(generatorConfig.mappingXMLPackageProperty());
        mappingTargetProject.textProperty().bindBidirectional(generatorConfig.mappingXMLTargetFolderProperty());
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


}
