package io.devpl.toolkit.fxui.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import org.fxmisc.richtext.CodeArea;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.squareup.javapoet.ClassName;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.FieldInfo;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import io.devpl.toolkit.fxui.view.navigation.impl.ConnectionItem;
import io.devpl.toolkit.fxui.view.navigation.impl.DatabaseNavigationView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * 类编辑器
 */
@FxmlLocation(location = "static/fxml/class_definition.fxml", title = "类编辑")
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
    public TableColumn<FieldInfo, ClassName> tblcFieldDataType;
    @FXML
    public TableColumn<FieldInfo, String> tblcFieldName;
    @FXML
    public TableColumn<FieldInfo, String> tblcFieldRemarks;
    @FXML
    public TitledPane titpFieldList;
    @FXML
    public Button btnClassGenerate;

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
        // tblcFieldDataType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()));
        tblcFieldDataType.setCellFactory(param -> {
            ChoiceBoxTableCell<FieldInfo, ClassName> cell = new ChoiceBoxTableCell<>(new StringConverter<ClassName>() {
                @Override
                public String toString(ClassName object) {
                    return null;
                }

                @Override
                public ClassName fromString(String string) {
                    return null;
                }
            });
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
            items.remove(selectedIndex);
        }
    }

    @FXML
    public void openFieldImportDialog(ActionEvent actionEvent) {
        StageHelper.show(PojoEditorController.class);
    }
    
    @FXML
    public void openColumnChooserDialog(ActionEvent actionEvent) {
    	DatabaseNavigationView dbNavigationView = new DatabaseNavigationView();
    	Scene scene = new Scene(dbNavigationView);
    	
    	Collection<ConnectionConfig> connectionInfos = ConnectionRegistry.getConnectionConfigurations();
    	
    	connectionInfos.forEach(item -> {
    		ConnectionItem connItem = new ConnectionItem();
    		connItem.setConnectionConfig(item);
    		dbNavigationView.addConnections(Arrays.asList(connItem));
    	});
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    public void generateClassDefinition(MouseEvent mouseEvent) {
        ObservableList<FieldInfo> items = tbvFieldInfo.getItems();

        String packageName = "";

        for (FieldInfo item : items) {
            System.out.println(item.getDataType());
        }

    }
}
