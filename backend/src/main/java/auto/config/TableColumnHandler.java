package auto.config;

import auto.pojo.ColumnPOJO;
import auto.pojo.TablePOJO;
import org.springframework.beans.BeanUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class TableColumnHandler {
    /**
     * 封装表名、列名、列类型
     *
     * @return
     */
    public static List<TablePOJO> tableColumnType() throws Exception {
        List<TablePOJO> tableList = new ArrayList<>();
        ResultSet rs = DatabaseConfig.getConnection("show tables");
        while (rs.next()) {
            // 表名
            Object tableName = rs.getObject(1);
            tableList.add(new TablePOJO((String) tableName, getColumn(tableName)));
        }
        DatabaseConfig.close();
        return tableList;
    }


    public static List<TablePOJO> tableColumnType(String tableName) throws Exception {
        List<TablePOJO> tableList = new ArrayList<>();
        tableList.add(new TablePOJO(tableName, getColumn(tableName)));
        return tableList;
    }

    /**
     * 根据表名来返回该表对应列名和列类型
     *
     * @param table
     * @return
     */
    public static List<ColumnPOJO> getColumn(Object table) throws Exception {
        List<ColumnPOJO> columnList = new ArrayList<>();
        ResultSet tableName = DatabaseConfig.getConnection("select * from " + table);
        ResultSetMetaData rsmd = tableName.getMetaData();

        // 字段总数
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            /**获取字段备注**/
            String comment = getColumnComment(table, rsmd.getColumnName(i));
            String humpColumnName = BeanUtil.toHump(rsmd.getColumnName(i), 1);
            String javaType = getJavaType(rsmd.getColumnClassName(i));
            String jdbcType = getMyBatisType(rsmd.getColumnTypeName(i));
            //默认第一个字段就是主键
            if (i == 1)
                columnList.add(new ColumnPOJO(rsmd.getColumnName(i), humpColumnName, jdbcType, comment, javaType, true));
            else
                columnList.add(new ColumnPOJO(rsmd.getColumnName(i), humpColumnName, jdbcType, comment, javaType, false));
        }
        return columnList;
    }


    /**
     * 获取字段备注
     *
     * @param table
     * @param column
     * @return
     */
    public static String getColumnComment(Object table, String column) throws Exception {
        String comment = "";
        ResultSet columnDate = DatabaseConfig.getConnection("SHOW FULL COLUMNS FROM  " + table + " WHERE FIELD='" + column + "'");
        while (columnDate.next()) {
            comment = columnDate.getString("Comment");
        }
        columnDate.close();
        DatabaseConfig.close();
        return comment;
    }

    /**
     * 部分类型处理
     *
     * @param className
     * @return
     */
    public static String getJavaType(String className) {
        String javaType;
        if (className != null && className.contains(".")) {
            javaType = className.substring(className.lastIndexOf(".") + 1);
            if (javaType.equals("Date") || javaType.equals("Datetime") || javaType.equals("Timestamp")) {
                javaType = "Date";
            } else if (javaType.equals("BigDecimal")) {
                javaType = "Double";
            } else if (javaType.equals("Time")) {
                javaType = "String";
            }
            return javaType;
        } else if (className != null && className.contains("[B")) {
            return "byte[]";
        } else {
            return className;
        }
    }

    /**
     * 部分类型处理
     *
     * @param typeName
     * @return
     */
    public static String getMyBatisType(String typeName) {
        if (typeName.equals("DATE") || typeName.equals("DATETIME")) {
            return "TIMESTAMP";
        } else if (typeName.equals("TEXT") || typeName.equals("Time")) {
            return "VARCHAR";
        }
        return typeName;
    }
}
