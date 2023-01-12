package io.devpl.codegen.sql;

public class Utils {

    /**
     * 使用反单引号进行包裹 '`'
     * @return ``
     */
    public static String wrapWithBackquote(String columnName) {
        if (!columnName.startsWith("`")) {
            columnName = "`" + columnName;
        }
        if (!columnName.endsWith("`")) {
            columnName = columnName + "`";
        }
        return columnName;
    }
}
