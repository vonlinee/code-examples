package io.maker.generator.sample;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.base.CaseFormat;
import io.maker.generator.db.JdbcUtils;
import io.maker.generator.db.meta.column.ColumnInfoSchema;
import io.maker.generator.db.meta.column.ColumnMetaDataLoader;
import io.maker.generator.db.meta.table.TableMetaData;
import io.maker.generator.db.meta.table.TableMetaDataLoader;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class SQLGenerator {

    public Map<String, String> insertValue() {
        Map<String, String> columnValueMapping = new HashMap<>();
        columnValueMapping.put("", "");
        return columnValueMapping;
    }

    static Predicate<String> filter = columnName -> {
        List<String> rules = new ArrayList<>();
        return columnName.contains("COLUMN") || columnName.contains("_MYCAT_OP_TIME");
    };

    public static void main(String[] args) throws Exception {
        Properties properties = JdbcUtils.getProperties();
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);


//		TableInfoSchema tableInfoSchema = TableMetaDataLoader.loadSchema(dataSource, "mp", "t_usc_mdm_user_dlr");
//
//		System.out.println(tableInfoSchema);
//

//        String sql = generateInsertSql(dataSource, "t_sac_onetask_receive_object", "MySQL", filter);
//        System.out.println(sql);

        ColumnInfoSchema schema = ColumnMetaDataLoader.loadInfomationSchema(dataSource, "db_mysql", "course");
        System.out.println(schema);
    }

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

}
