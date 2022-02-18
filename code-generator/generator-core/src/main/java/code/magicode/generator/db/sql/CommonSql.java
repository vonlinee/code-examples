package code.magicode.generator.db.sql;

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

import code.magicode.generator.db.meta.table.TableInfoSchema;
import code.magicode.generator.db.meta.table.TableMetaData;
import code.magicode.generator.db.meta.table.TableMetaDataLoader;
import io.maker.base.ResourceLoader;

public class CommonSql {
	
	public Map<String, String> insertValue() {
		Map<String, String> columnValueMapping = new HashMap<>();
		columnValueMapping.put("", "");
		return columnValueMapping;
	}

	public static void main(String[] args) throws Exception {
		Properties properties = ResourceLoader.loadProperties("druid.properties");
		DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
		
		
//		TableInfoSchema tableInfoSchema = TableMetaDataLoader.loadSchema(dataSource, "mp", "t_usc_mdm_user_dlr");
//		
//		System.out.println(tableInfoSchema);
		
		
		Predicate<String> filter = columnName -> {
			List<String> rules = new ArrayList<>();
			return columnName.contains("COLUMN") || columnName.contains("_MYCAT_OP_TIME") || columnName.contains("_MYCAT_OP_TIME");
		};
		
		String sql = generateInsertSql(dataSource, "t_sac_onetask_receive_object", "MySQL", filter);
		
		System.out.println(sql);
	}

	public static String generateInsertSql(DataSource dataSource, String tableName, String databaseType, Predicate<String> filter) throws SQLException {
    	StringBuilder sql = new StringBuilder("INSERT INTO `" + tableName + "` ");
    	filter = filter == null ? s -> false : filter;
    	TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, tableName, databaseType);
    	List<String> columnNames = tableMetaData.getColumnNames();
    	StringJoiner insertFields =  new StringJoiner(",", "(", ")");
    	StringJoiner insertFieldValues = new StringJoiner(",", "(", ")");
    	for(String columnName :columnNames) {
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
