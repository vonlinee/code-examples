package io.devpl.toolkit.fxui.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import io.devpl.codegen.mbpg.jdbc.dialect.mysql.InfoSchemaColumn;
import io.devpl.codegen.sql.SqlUtils;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.toolkit.fxui.model.FieldInfo;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 实体类编辑控制器
 */
@FxmlLocation(location = "static/fxml/pojo_editor.fxml", title = "实体类编辑")
public class PojoEditorController extends FxmlView {

    @FXML
    public TextArea txaContent;
    @FXML
    public TextField txfDbName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
