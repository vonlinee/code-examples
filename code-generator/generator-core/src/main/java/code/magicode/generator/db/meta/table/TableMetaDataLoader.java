package code.magicode.generator.db.meta.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import code.magicode.generator.db.meta.column.ColumnMetaDataLoader;
import code.magicode.generator.db.meta.index.IndexMetaDataLoader;

/**
 * Table meta data loader.
 */
public final class TableMetaDataLoader {

	private TableMetaDataLoader() {}

	/**
	 * Load table meta data.
	 * @param dataSource data source
	 * @param table table name
	 * @param databaseType database type
	 * @return table meta data
	 * @throws SQLException SQL exception
	 */
	public static TableMetaData load(final DataSource dataSource, final String table, final String databaseType)
			throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			return new TableMetaData(ColumnMetaDataLoader.load(connection, table, databaseType),
					IndexMetaDataLoader.load(connection, table, databaseType));
		}
	}
	
	/**
	 * 获取表的Schema信息
	 * @param dataSource
	 * @param dbName
	 * @param tableName
	 * @return
	 */
	public static TableSchema loadSchema(final DataSource dataSource, final String dbName, final String tableName) {
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM `information_schema`.`TABLES` T WHERE T.TABLE_SCHEMA = '%s' AND T.TABLE_NAME = '%s'", dbName, tableName));
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		loadSchema(null, "information_schema", "course");
	}
}
