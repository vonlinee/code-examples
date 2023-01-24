package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.model.FieldInfo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.fxmisc.richtext.CodeArea;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.java.render.TopLevelClassRenderer;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 类编辑器
 */
@FxmlLocation(location = "static/fxml/class_definition.fxml")
public class ClassDefinitionController extends FxmlView {

    @FXML
    public CodeArea codePreview;
    @FXML
    public VBox vboxInputRoot;
    @FXML
    public VBox vboxClassBasicInfoRoot;
    @FXML
    public TextField txfClassName;
    @FXML
    public Accordion acdFieldMethodInfoRoot;
    @FXML
    public TableView<FieldInfo> tbvFieldInfo;
    @FXML
    public TableColumn<FieldInfo, String> tblcFieldModifier;
    @FXML
    public TableColumn<FieldInfo, String> tblcFieldDataType;
    @FXML
    public TableColumn<FieldInfo, String> tblcFieldName;
    @FXML
    public TableColumn<FieldInfo, String> tblcFieldRemarks;
    @FXML
    public TitledPane titpFieldList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acdFieldMethodInfoRoot.setExpandedPane(titpFieldList);
        // 支持多选
        tbvFieldInfo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        acdFieldMethodInfoRoot.prefHeightProperty()
                .bind(vboxInputRoot.heightProperty().subtract(vboxClassBasicInfoRoot.heightProperty()));
        tbvFieldInfo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcFieldModifier.setCellValueFactory(param -> param.getValue().modifierProperty());
        tblcFieldModifier.setCellFactory(param -> {
            ChoiceBoxTableCell<FieldInfo, String> cell = new ChoiceBoxTableCell<>();
            cell.getItems().addAll("public", "private", "protected");
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        tblcFieldDataType.setCellValueFactory(param -> param.getValue().dataTypeProperty());
        tblcFieldDataType.setCellFactory(param -> {
            ChoiceBoxTableCell<FieldInfo, String> cell = new ChoiceBoxTableCell<>(new DefaultStringConverter());
            cell.getItems().addAll("int", "String", "float");
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        tblcFieldName.setCellValueFactory(param -> param.getValue().nameProperty());
        tblcFieldName.setCellFactory(param -> {
            TextFieldTableCell<FieldInfo, String> cell = new TextFieldTableCell<>(new DefaultStringConverter());
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        tblcFieldRemarks.setCellValueFactory(param -> param.getValue().remarksProperty());
        tblcFieldRemarks.setCellFactory(param -> {
            TextFieldTableCell<FieldInfo, String> cell = new TextFieldTableCell<>(new DefaultStringConverter());
            cell.setAlignment(Pos.CENTER);
            cell.setEditable(true);
            return cell;
        });
    }

    @FXML
    public void addField(ActionEvent actionEvent) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setModifier("private");
        fieldInfo.setDataType("String");
        fieldInfo.setRemarks("字段注释");
        tbvFieldInfo.getItems().add(fieldInfo);
    }

    @Subscribe(name = "addFieldInfoList", threadMode = ThreadMode.BACKGROUND)
    public void addFields(List<FieldInfo> fieldInfoList) {
        System.out.println(fieldInfoList);
        for (FieldInfo fieldInfo : fieldInfoList) {
            tbvFieldInfo.getItems().add(fieldInfo);
        }
    }

    @FXML
    public void deleteField(ActionEvent actionEvent) {
        ObservableList<Integer> selectedIndices = tbvFieldInfo.getSelectionModel().getSelectedIndices();
        if (selectedIndices == null || selectedIndices.isEmpty()) {
            return;
        }
        List<FieldInfo> items = tbvFieldInfo.getItems();
        for (int selectedIndex : selectedIndices) {
            System.out.println(selectedIndex);
            items.remove(selectedIndex);
        }
    }

    @FXML
    public void openFieldImportDialog(ActionEvent actionEvent) {
        StageHelper.show("实体类编辑", PojoEditorController.class);
    }
}
