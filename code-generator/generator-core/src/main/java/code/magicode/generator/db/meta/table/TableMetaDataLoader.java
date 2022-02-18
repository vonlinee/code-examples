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
	public static TableInfoSchema loadSchema(final DataSource dataSource, final String dbName, final String tableName) {
		String sql = String.format("SELECT * FROM `information_schema`.`TABLES` T WHERE T.TABLE_SCHEMA = '%s' AND T.TABLE_NAME = '%s'", dbName, tableName);
		TableInfoSchema schema = new TableInfoSchema();
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
			try (ResultSet resultSet = statement.executeQuery(sql)) {
				while (resultSet.next()) {
					schema.setTableCatalog(resultSet.getString("TABLE_CATALOG"));// varchar(64)
					schema.setTableSchema(resultSet.getString("TABLE_SCHEMA"));// varchar(64)
					schema.setTableName(resultSet.getString("TABLE_NAME"));// varchar(64)
					schema.setTableType(resultSet.getString("TABLE_TYPE"));// enum BASE_TABLE/VIEW/SYSTEM_VIEW
					schema.setEngine(resultSet.getString("ENGINE"));// varchar(64)
					schema.setVersion(resultSet.getInt("VERSION"));// int(2)
					schema.setRowFormat(resultSet.getString("ROW_FORMAT"));// enum Fixed, Dynamic, Compressed, Redundant, Compact, Paged
					schema.setTableRows(resultSet.getLong("TABLE_ROWS"));// bigint(21) unsigned
					schema.setAvgRowLength(resultSet.getLong("AVG_ROW_LENGTH"));// bigint(21) unsigned
					schema.setDataLength(resultSet.getLong("DATA_LENGTH"));// bigint(21) unsigned
					schema.setMaxDataLength(resultSet.getLong("MAX_DATA_LENGTH"));// bigint(21) unsigned
					schema.setIndexLength(resultSet.getLong("INDEX_LENGTH"));// bigint(21) unsigned
					schema.setDataFree(resultSet.getLong("DATA_FREE"));// bigint(21) unsigned
					schema.setAutoIncrement(resultSet.getLong("AUTO_INCREMENT"));// bigint(21) unsigned
					schema.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));// timestamp
					schema.setUpdateTime(resultSet.getTimestamp("UPDATE_TIME"));// datetime
					schema.setCheckTime(resultSet.getTimestamp("CHECK_TIME"));// datetime
					schema.setTableCollation(resultSet.getString("TABLE_COLLATION"));// varchar(64)
					schema.setChecksum(resultSet.getLong("CHECKSUM"));// bigint(21)
					schema.setCreateOptions(resultSet.getString("CREATE_OPTIONS"));// varchar(256)
					schema.setTableComment(resultSet.getString("TABLE_COMMENT"));// text
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schema;
	}
}
