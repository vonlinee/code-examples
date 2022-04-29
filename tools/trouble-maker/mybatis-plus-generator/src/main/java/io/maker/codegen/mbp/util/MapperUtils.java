package io.maker.codegen.mbp.util;

import io.maker.base.utils.CaseFormat;
import io.maker.codegen.mbp.config.po.TableField;
import io.maker.codegen.mbp.config.po.TableInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MapperUtils {

    public static final Predicate<String> columnNameFilter = columnName -> {
        List<String> ignoreColumnNames = new ArrayList<>();
        ignoreColumnNames.add("COLUMN1");
        ignoreColumnNames.add("COLUMN2");
        ignoreColumnNames.add("COLUMN3");
        ignoreColumnNames.add("COLUMN4");
        return ignoreColumnNames.contains(columnName);
    };

    public static void tableField(TableInfo tableInfo, String tableAlias) {
        List<TableField> fields = tableInfo.getFields();
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(tableInfo.getName()).append(" (\n");

        StringBuilder insertValues = new StringBuilder();
        for (TableField field : fields) {
            String comment = field.getComment();
            String columnName = tableAlias + "." + field.getColumnName();
            sb.append("\t").append(columnName).append(", /*").append(comment).append("*/\n");

            // 特殊值

            String columnCamelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field.getColumnName());
            insertValues.append("\t#{param.").append(columnCamelName).append("},\n");
        }

        sb.append("VALUES (\n").append(insertValues).append(")");

        System.out.println(sb);
    }

    /**
     * 生成insert tag
     * @return
     */
    public static String insertTag() {
        return "";
    }
}
