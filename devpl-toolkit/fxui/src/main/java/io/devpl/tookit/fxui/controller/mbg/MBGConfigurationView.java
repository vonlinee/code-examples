package io.devpl.tookit.fxui.controller.mbg;

import io.devpl.fxtras.beans.ValueUpdateListener;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.model.CodeGenConfiguration;
import io.devpl.tookit.utils.fx.FileChooserDialog;
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
    public TextField mappingTargetProject;
    @FXML
    public TextField daoTargetProject;
    @FXML
    public TextField projectFolderField;

    /**
     * 通用配置项
     */
    private final CodeGenConfiguration codeGenConfig = new CodeGenConfiguration();
    public Button btnSaveConfig;

    public Button btnLoadConfig;
    @FXML
    public ChoiceBox<String> cboxProjectLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCodeGenConfiguration(codeGenConfig);

        cboxProjectLayout.setConverter();
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
    public void bindCodeGenConfiguration(CodeGenConfiguration generatorConfig) {
        ValueUpdateListener.bind(projectFolderField.textProperty(), generatorConfig, CodeGenConfiguration::setProjectFolder);
        modelTargetPackage.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setModelPackage));
        modelTargetProject.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setModelPackageTargetFolder));
        txfParentPackageName.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setParentPackage));
        txfMapperPackageName.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setDaoPackage));
        daoTargetProject.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setDaoTargetFolder));
        mapperTargetPackage.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setMappingXMLPackage));
        mappingTargetProject.textProperty()
                .addListener(new ValueUpdateListener<>(generatorConfig, CodeGenConfiguration::setMappingXMLTargetFolder));
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
        System.out.println(codeGenConfig);
    }
}
