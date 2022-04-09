package io.maker.generator.sample;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.function.Predicate;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.base.CaseFormat;

import io.maker.generator.db.JdbcUtils;
import io.maker.generator.db.meta.table.TableMetaData;
import io.maker.generator.db.meta.table.TableMetaDataLoader;
import io.maker.generator.utils.MyBatis;

public class MyBatisXMLGenerator {

    public Map<String, String> insertValue() {
        Map<String, String> columnValueMapping = new HashMap<>();
        columnValueMapping.put("", "");
        return columnValueMapping;
    }

    private static final Predicate<String> columnValueFilter = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            return false;
        }
    };

    private static Predicate<String> columnNameFilter = columnName -> {
        List<String> rules = new ArrayList<>();
        return columnName.contains("COLUMN") || columnName.contains("_MYCAT_OP_TIME");
    };

    public static void main(String[] args) throws Exception {
        Properties properties = JdbcUtils.getLocalProperties();
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        MyBatis mybatis = new MyBatis(dataSource);
//		TableInfoSchema tableInfoSchema = TableMetaDataLoader.loadSchema(dataSource, "mp", "t_usc_mdm_user_dlr");
//
//		System.out.println(tableInfoSchema);
//
//        String sql = generateInsertSql(dataSource, "jsh_user", "MySQL", columnNameFilter);
//        System.out.println(sql);
        generateSelectXml(dataSource, "t_usc_mdm_user_dlr", "MySQL", columnNameFilter);
    }

    /**
     * 生成单表插入SQL
     * @param dataSource
     * @param tableName
     * @param databaseType
     * @param filter
     * @return
     * @throws SQLException
     */
    public static String generateInsertSql(DataSource dataSource, String tableName, String databaseType, Predicate<String> filter) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO `" + tableName + "` ");
        filter = filter == null ? s -> false : filter;
        TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, tableName, databaseType);
        List<String> columnNames = tableMetaData.getColumnNames();
        StringJoiner insertFields = new StringJoiner(",", "(", ")");
        StringJoiner insertFieldValues = new StringJoiner(",", "(", ")");
        for (String columnName : columnNames) {
            if (filter.test(columnName)) {
                continue;
            }
            insertFields.add("\n\t" + columnName);
            String camelColumnName = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL).convert(columnName);
            insertFieldValues.add("\n\t#{param." + camelColumnName + "}");
        }
        sql.append(insertFields.toString()).append("\n").append(" VALUES ").append(insertFieldValues.toString());
        return sql.toString();
    }

    /**
     * 生成单表查询的XML中的SQL语句
     * @param dataSource
     * @param tableName
     * @param databaseType
     * @param filter
     * @return
     * @throws SQLException
     */
    public static String generateSelectXml(DataSource dataSource, String tableName, String databaseType, Predicate<String> filter) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT");
        String tableAlias = "T";
        TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, tableName, databaseType);
        List<String> columnNames = tableMetaData.getColumnNames();
        StringJoiner insertFields = new StringJoiner(",", "(", ")");
        StringJoiner insertFieldValues = new StringJoiner(",", "(", ")");
        for (String columnName : columnNames) {
            if (filter == null || filter.test(columnName)) {
                continue;
            }
            insertFields.add("\n\t" + columnName);
            String camelColumnName = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL).convert(columnName);
            insertFieldValues.add("\n\t#{param." + camelColumnName + "}");
        }
        sql.append(insertFields).append("\n").append(" VALUES ").append(insertFieldValues.toString());
        return sql.toString();
    }

    abstract static class XMLFragment {
        String type; /* select, update, insert , delete , sql */
        String sqlContent;
    }

    static class SelectTag extends XMLFragment {

        public SelectTag() {
            this.type = "select";
        }

        private int id;
        private String parameterType;
        private String resultType;
    }

    static class Column {
        String name;
        String value;
        boolean toBeTest;
    }
}
