package io.maker.codegen.core.db.meta.schema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.apache.shardingsphere.sql.parser.binder.metadata.column.ColumnMetaDataLoader;
import org.apache.shardingsphere.sql.parser.binder.metadata.index.IndexMetaDataLoader;
import org.apache.shardingsphere.sql.parser.binder.metadata.table.TableMetaData;
import org.apache.shardingsphere.sql.parser.binder.metadata.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Schema meta data loader.
 */
public class SchemaMetaDataLoader {

	private static final Logger LOG = LoggerFactory.getLogger(SchemaMetaDataLoader.class);

	private static final String TABLE_TYPE = "TABLE";
	private static final String TABLE_NAME = "TABLE_NAME";

	/**
	 * Load schema meta data.
	 * 
	 * @param dataSource         data source
	 * @param maxConnectionCount count of max connections permitted to use for this
	 *                           query
	 * @param databaseType       database type
	 * @return schema meta data
	 * @throws SQLException SQL exception
	 */
	public static SchemaMetaData load(final DataSource dataSource, final int maxConnectionCount,
			final String databaseType) throws SQLException {
		List<String> tableNames;
		try (Connection connection = dataSource.getConnection()) {
			tableNames = loadAllTableNames(connection, databaseType);
		}
		LOG.info("Loading {} tables' meta data.", tableNames.size());
		if (tableNames.isEmpty()) {
			return new SchemaMetaData(Collections.emptyMap());
		}
		List<List<String>> tableGroups = Lists.partition(tableNames,
				Math.max(tableNames.size() / maxConnectionCount, 1));
		Map<String, TableMetaData> tableMetaDataMap = 1 == tableGroups.size()
				? load(dataSource.getConnection(), tableGroups.get(0), databaseType)
				: asyncLoad(dataSource, maxConnectionCount, tableNames, tableGroups, databaseType);
		return new SchemaMetaData(tableMetaDataMap);
	}

	private static Map<String, TableMetaData> load(final Connection connection, final Collection<String> tables,
			final String databaseType) throws SQLException {
		try (Connection con = connection) {
			Map<String, TableMetaData> result = new LinkedHashMap<>();
			for (String each : tables) {
				result.put(each, new TableMetaData(ColumnMetaDataLoader.load(con, each, databaseType),
						IndexMetaDataLoader.load(con, each, databaseType)));
			}
			return result;
		}
	}

	/**
	 * 加载所有的表名
	 * @param connection
	 * @param databaseType
	 * @return
	 * @throws SQLException
	 */
	private static List<String> loadAllTableNames(
			final Connection connection, final String databaseType) throws SQLException {
		List<String> result = new LinkedList<>();
		try (ResultSet resultSet = connection.getMetaData().getTables(connection.getCatalog(),
				JdbcUtil.getSchema(connection, databaseType), null, new String[] { TABLE_TYPE })) {
			while (resultSet.next()) {
				String table = resultSet.getString(TABLE_NAME);
				if (!isSystemTable(table)) {
					result.add(table);
				}
			}
		}
		return result;
	}

	private static boolean isSystemTable(final String table) {
		return table.contains("$") || table.contains("/");
	}

	private static Map<String, TableMetaData> asyncLoad(final DataSource dataSource, final int maxConnectionCount,
			final List<String> tableNames,
			final List<List<String>> tableGroups, final String databaseType) throws SQLException {
		Map<String, TableMetaData> result = new ConcurrentHashMap<>(tableNames.size(), 1);
		ExecutorService executorService = Executors
				.newFixedThreadPool(Math.min(tableGroups.size(), maxConnectionCount));
		Collection<Future<Map<String, TableMetaData>>> futures = new LinkedList<>();
		for (List<String> each : tableGroups) {
			futures.add(executorService.submit(() -> load(dataSource.getConnection(), each, databaseType)));
		}
		for (Future<Map<String, TableMetaData>> each : futures) {
			try {
				result.putAll(each.get());
			} catch (final InterruptedException | ExecutionException ex) {
				if (ex.getCause() instanceof SQLException) {
					throw (SQLException) ex.getCause();
				}
				Thread.currentThread().interrupt();
			}
		}
		return result;
	}
}
