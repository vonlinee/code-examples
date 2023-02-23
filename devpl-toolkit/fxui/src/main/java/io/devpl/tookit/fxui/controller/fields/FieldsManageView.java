package io.devpl.tookit.fxui.controller.fields;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.tookit.fxui.model.FieldSpec;
import io.devpl.tookit.utils.CollectionUtils;
import io.devpl.tookit.utils.StringUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public void addNewField(ActionEvent actionEvent) {
        FieldSpec metaField = new FieldSpec();
        metaField.setFieldName("name");
        metaField.setFieldDescription("描述文本");
        tbvMetaFields.getItems().add(metaField);
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
        ObservableList<Integer> selectedIndices = tbvMetaFields.getSelectionModel().getSelectedIndices();
        final ObservableList<FieldSpec> items = tbvMetaFields.getItems();
        if (CollectionUtils.isNotEmpty(selectedIndices)) {
            for (Integer selectedIndex : selectedIndices) {
                items.remove(selectedIndex.intValue());
                System.out.println(selectedIndex);
            }
        }
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
            tab.setContent(newTabContentByImportType(importType));
            tab.setClosable(false);
            tbpImportContent.getTabs().add(tab);
        }
    }

    private Node newTabContentByImportType(ImportType importType) {
        Node node;
        if (importType == ImportType.SQL) {
            node = ViewLoader.load(SQLImportView.class).getRoot();
        } else if (importType == ImportType.JSON) {
            node = ViewLoader.load(JsonImportView.class).getRoot();
        } else {
            node = new Button("Button");
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
        tblcFieldName.setEditable(true);
        tblcFieldDescription.setCellValueFactory(new PropertyValueFactory<>("fieldDescription"));
        tblcFieldDescription.setEditable(true);
        tblcFieldValue.setCellValueFactory(new PropertyValueFactory<>("fieldValue"));
        tblcFieldValue.setEditable(true);
    }

    @FXML
    public void parseFieldsFromInput(ActionEvent actionEvent) {
        Tab selectedItem = tbpImportContent.getSelectionModel().getSelectedItem();
        Node content = selectedItem.getContent();
        String tabName = selectedItem.getText();
        ImportType importType = ImportType.valueOf(tabName);
        TextArea text = (TextArea) content;
        String input = text.getText();

        if (StringUtils.hasNotText(input)) {
            Alerts.error("文本不能为空!").showAndWait();
            return;
        }

        if (importType == ImportType.JSON) {
            List<FieldSpec> list = extractFieldsFromJson(input);
            tbvMetaFields.getItems().addAll(list);
        }
    }

    /**
     * 解析JSON
     *
     * @param input json文本
     * @return 字段列表
     */
    private List<FieldSpec> extractFieldsFromJson(String input) {
        List<FieldSpec> list = new ArrayList<>();
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(input, JsonElement.class);
        fill(list, jsonElement);
        return list;
    }

    /**
     * 提取所有的Key，不提取值
     *
     * @param fieldList
     * @param jsonElement
     */
    private void fill(List<FieldSpec> fieldList, JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            JsonObject jobj = jsonElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jobj.entrySet()) {
                final JsonElement value = entry.getValue();
                if (value.isJsonNull() || value.isJsonPrimitive()) {
                    final FieldSpec metaField = new FieldSpec();
                    metaField.setFieldName(entry.getKey());
                    fieldList.add(metaField);
                } else {
                    fill(fieldList, value);
                }
            }
        } else if (jsonElement.isJsonArray()) {
            final JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray.asList()) {
                fill(fieldList, element);
            }
        }
    }
}
