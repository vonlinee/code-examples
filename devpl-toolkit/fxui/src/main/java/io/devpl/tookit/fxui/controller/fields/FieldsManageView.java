package io.devpl.tookit.fxui.controller.fields;

import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.tookit.fxui.model.FieldSpec;
import io.devpl.tookit.utils.fx.CellUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 字段元信息管理
 * 没有数据类型，只有字段名和字段值
 */
@FxmlLocation(location = "layout/fields/FieldsManageView.fxml", title = "字段管理")
public class FieldsManageView extends FxmlView {

    @FXML
    public TableView<FieldSpec> tbvMetaFields; // 临时存储导入的字段信息
    @FXML
    public TableColumn<FieldSpec, Object> tblcOperations;
    @FXML
    public TableColumn<FieldSpec, String> tblcFieldName;
    @FXML
    public TableColumn<FieldSpec, String> tblcFieldValue;
    @FXML
    public TableColumn<FieldSpec, String> tblcFieldDescription;
    @FXML
    public TabPane tbpImportContent;
    @FXML
    public TextField txfSearchField;
    @FXML
    public Button btnSearchSubmit;
    @FXML
    public CheckBox chbAllowDuplicateFieldName;

    @FXML
    public void addNewField(ActionEvent actionEvent) {

    }

    @Subscribe(name = "AddFields", threadMode = ThreadMode.BACKGROUND)
    public void addFields(List<FieldSpec> fieldSpecList) {
        final ObservableList<FieldSpec> items = tbvMetaFields.getItems();
        if (chbAllowDuplicateFieldName.isSelected()) {
            items.addAll(fieldSpecList);
        } else {
            // TODO list可能数据量大的时候很慢
            for (FieldSpec fieldSpec : fieldSpecList) {
                if (!items.contains(fieldSpec)) {
                    items.add(fieldSpec);
                }
            }
        }
    }

    /**
     * 将所有导入的字段保存到数据库
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void saveToDatabase(ActionEvent actionEvent) {
        ObservableList<FieldSpec> items = tbvMetaFields.getItems();
    }

    @FXML
    public void deleteSelectedFields(ActionEvent actionEvent) {
        CellUtils.removeSelected(tbvMetaFields);
    }

    @FXML
    public void parse(ActionEvent actionEvent) {
        final FieldImportEvent event = new FieldImportEvent();
        event.setTableView(this.tbvMetaFields);
        publish(event);
    }

    /**
     * 导入类型
     */
    enum ImportType {
        JSON,
        SQL,
        TEXT
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeFieldTableView();
        // 初始化所有Tab
        for (ImportType importType : ImportType.values()) {
            Tab tab = new Tab(importType.name());
            Node node = newTabContentByImportType(importType);
            if (node == null) {
                continue;
            }
            tab.setContent(node);
            tab.setClosable(false);
            tbpImportContent.getTabs().add(tab);
        }
    }

    /**
     * 导入类型决定节点
     *
     * @param importType 导入类型
     * @return
     */
    private Node newTabContentByImportType(ImportType importType) {
        Node node;
        if (importType == ImportType.SQL) {
            node = ViewLoader.load(SQLImportView.class).getRoot();
        } else if (importType == ImportType.JSON) {
            node = ViewLoader.load(JsonImportView.class).getRoot();
        } else {
            return null;
        }
        return node;
    }

    /**
     * 初始化字段信息表
     */
    private void initializeFieldTableView() {
        tbvMetaFields.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // 多选
        tbvMetaFields.setEditable(true);
        tbvMetaFields.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcFieldName.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        tblcFieldName.setCellFactory(param -> {
            TableCell<FieldSpec, String> tableCell = new TextFieldTableCell<>();
            tableCell.setAlignment(Pos.CENTER);
            return tableCell;
        });
        tblcFieldName.setEditable(true);
        tblcFieldDescription.setCellValueFactory(new PropertyValueFactory<>("fieldDescription"));
        tblcFieldDescription.setEditable(true);
        tblcFieldDescription.setCellFactory(param -> {
            TableCell<FieldSpec, String> tableCell = new TextFieldTableCell<>();
            tableCell.setAlignment(Pos.CENTER);
            return tableCell;
        });
        tblcFieldValue.setCellValueFactory(new PropertyValueFactory<>("fieldValue"));
        tblcFieldValue.setEditable(true);
    }
}
