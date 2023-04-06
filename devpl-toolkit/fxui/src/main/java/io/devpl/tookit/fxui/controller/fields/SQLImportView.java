package io.devpl.tookit.fxui.controller.fields;

import io.devpl.codegen.mbpg.jdbc.dialect.mysql.InfoSchemaColumn;
import io.devpl.codegen.sql.SqlUtils;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.tookit.fxui.editor.CodeMirrorEditor;
import io.devpl.tookit.fxui.editor.LanguageMode;
import io.devpl.tookit.fxui.model.FieldInfo;
import io.devpl.tookit.utils.DBUtils;
import io.devpl.tookit.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * SQL字段导入
 */
@FxmlLocation(location = "layout/fields/FieldsImportSQLView.fxml")
public class SQLImportView extends FxmlView {

    @FXML
    public ChoiceBox<String> chbJsonSpec;

    @FXML
    public TextField txfDbName;
    @FXML
    public BorderPane bopRoot;

    CodeMirrorEditor codeEditor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (codeEditor == null) {
            codeEditor = new CodeMirrorEditor();
            log.info("初始化编辑器");
            codeEditor.init(
                    () -> codeEditor.setContent("select * from T t where t.name = \"test\" limit 20;", true),
                    () -> codeEditor.setMode(LanguageMode.SQL),
                    () -> codeEditor.setTheme("xq-light"));
            bopRoot.setCenter(null);
            bopRoot.setCenter(codeEditor.getView());
        }
    }

    @FXML
    public void parseColumns(ActionEvent actionEvent) {
        String text = codeEditor.getContent();
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
