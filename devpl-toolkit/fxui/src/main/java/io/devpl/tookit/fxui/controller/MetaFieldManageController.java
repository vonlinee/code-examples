package io.devpl.tookit.fxui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.devpl.codegen.mbpg.jdbc.dialect.mysql.InfoSchemaColumn;
import io.devpl.codegen.sql.SqlUtils;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.model.FieldInfo;
import io.devpl.tookit.fxui.model.MetaField;
import io.devpl.tookit.utils.DBUtils;
import io.devpl.tookit.utils.StringUtils;
import io.devpl.tookit.fxui.view.IconMap;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 字段元信息管理
 */
@FxmlLocation(location = "layout/MetaFields.fxml", title = "字段元信息管理")
public class MetaFieldManageController extends FxmlView {

    @FXML
    public TableView<MetaField> tbvMetaFields;
    @FXML
    public TableColumn<MetaField, Object> tblcOperations;
    @FXML
    public TableColumn<MetaField, String> tblcFieldName;
    @FXML
    public TableColumn<MetaField, String> tblcFieldValue;
    @FXML
    public TableColumn<MetaField, String> tblcFieldDescription;
    @FXML
    public TabPane tbpImportContent;
    @FXML
    public TextField txfSearchField;
    @FXML
    public Button btnSearchSubmit;

    @FXML
    public TextArea txaContent;
    @FXML
    public TextField txfDbName;
    @FXML
    public TableColumn<MetaField, String> tblcSelected;

    @FXML
    public void addNewField(ActionEvent actionEvent) {
        MetaField metaField = new MetaField();
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
        ObservableList<MetaField> items = tbvMetaFields.getItems();
    }

    /**
     * 导入类型
     */
    enum ImportType {
        JSON,
        JSON_SCHEM,
        XML,
        TEXT,
        SQL
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
        Node node = null;
        if (importType == ImportType.SQL) {
            Button btn = new Button("解析字段");
            BorderPane borderPane = new BorderPane();
            Label label = new Label("数据库名称");
            TextField textField = new TextField("数据库名称");
            ToolBar toolBar = new ToolBar(btn, label, textField);
            borderPane.setTop(toolBar);
            node = borderPane;
        } else {
            node = new TextArea();
        }
        return node;
    }

    /**
     * 初始化字段信息表
     */
    private void initializeFieldTableView() {
        tblcFieldName.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        tblcFieldDescription.setCellValueFactory(new PropertyValueFactory<>("fieldDescription"));
        tblcFieldValue.setCellValueFactory(new PropertyValueFactory<>("fieldValue"));
        tblcSelected.setCellValueFactory(new PropertyValueFactory<>("selected"));
        tbvMetaFields.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcOperations.setCellFactory(param -> {
            Node closeIcon = IconMap.winodwCloase();
            TableCell<MetaField, Object> cell = new TableCell<>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(closeIcon);
                    }
                }
            };
            cell.setAlignment(Pos.CENTER);
            closeIcon.setOnMouseClicked(event -> {
                ObservableList<Integer> selectedIndices = tbvMetaFields.getSelectionModel().getSelectedIndices();
                for (Integer selectedIndex : selectedIndices) {
                    if (selectedIndex != null) {
                        tbvMetaFields.getItems().remove(selectedIndex.intValue());
                    }
                }
            });
            return cell;
        });
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
            List<MetaField> list = extractFieldsFromJson(input);
            tbvMetaFields.getItems().addAll(list);
        }
    }

    /**
     * 解析JSON
     *
     * @param input json文本
     * @return 字段列表
     */
    private List<MetaField> extractFieldsFromJson(String input) {
        List<MetaField> list = new ArrayList<>();
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
    private void fill(List<MetaField> fieldList, JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            JsonObject jobj = jsonElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jobj.entrySet()) {
                final JsonElement value = entry.getValue();
                if (value.isJsonNull() || value.isJsonPrimitive()) {
                    final MetaField metaField = new MetaField();
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

    @FXML
    public void parseColumns(ActionEvent actionEvent) {
        String text = txaContent.getText();
        if (StringUtils.hasNotText(text)) {
            Alerts.warn("待解析SQL为空!").showAndWait();
            return;
        }
        try {
            Map<String, Set<String>> map = SqlUtils.getSelectColumns(text);
            String dbName = txfDbName.getText();
            if (StringUtils.hasNotText(dbName)) {
                Alerts.warn("数据库名称为空!").show();
                return;
            }
            List<InfoSchemaColumn> metadata = new ArrayList<>();
            map.forEach((tableName, columnNames) -> {
                List<String> names = new ArrayList<>();
                for (String columnName : columnNames) {
                    names.add("'" + columnName + "'");
                }
                String sql = getQueryColumnMetaSql(dbName, tableName, names);
                metadata.addAll(query(sql));
            });
            List<FieldInfo> fieldInfos = new ArrayList<>();
            for (InfoSchemaColumn metadatum : metadata) {
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setModifier("private");
                fieldInfo.setDataType("String");
                fieldInfo.setName(StringUtils.underlineToCamel(metadatum.getColumnName()));
                fieldInfo.setRemarks(metadatum.getColumnComment());
                fieldInfos.add(fieldInfo);
            }
            publish("addFieldInfoList", fieldInfos);
            getStage(actionEvent).close();
        } catch (Exception exception) {
            Alerts.exception("解析失败", exception).showAndWait();
        }
    }

    public String getQueryColumnMetaSql(String databaseName, String tableName, Collection<String> columns) {
        String columnCondition = String.join(",", columns);
        return String.format("SELECT * FROM information_schema.`COLUMNS` " + "WHERE TABLE_SCHEMA = '%s' " + "AND TABLE_NAME = '%s' " + "AND COLUMN_NAME IN (%s)", databaseName, tableName, columnCondition);
    }

    public static List<InfoSchemaColumn> query(String sql) {
        String url = "jdbc:mysql://localhost:3306/ruoyi?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        try (Connection connection = DBUtils.getConnection(url, "root", "123456")) {
            return DBUtils.queryBeanList(connection, sql, InfoSchemaColumn.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
