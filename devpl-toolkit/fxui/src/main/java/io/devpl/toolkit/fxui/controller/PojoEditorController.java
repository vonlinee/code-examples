package io.devpl.toolkit.fxui.controller;

import io.devpl.codegen.mbpg.jdbc.dialect.mysql.InfoSchemaColumn;
import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.sql.SqlUtils;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.toolkit.fxui.model.FieldInfo;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@FxmlLocation(location = "static/fxml/pojo_editor.fxml")
public class PojoEditorController extends FxmlView {

    @FXML
    public TextArea txaContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void parseColumns(ActionEvent actionEvent) {
        String text = txaContent.getText();
        if (StringUtils.isBlank(text)) {
            Alerts.warn("待解析SQL为空!").showAndWait();
            return;
        }
        try {
            Map<String, Set<String>> map = SqlUtils.getSelectColumns(text);
            String dbName = "ruoyi";
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
