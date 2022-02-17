package code.magicode.generator.db.meta.table;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

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
}
