package io.maker.generator.db.meta.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shardingsphere.sql.parser.binder.metadata.table.TableMetaData;

/**
 * Schema meta data.
 */
public final class SchemaMetaData {

	private final Map<String, TableMetaData> tables;

	public SchemaMetaData(final Map<String, TableMetaData> tables) {
		this.tables = new ConcurrentHashMap<>(tables.size(), 1);
		for (Entry<String, TableMetaData> entry : tables.entrySet()) {
			this.tables.put(entry.getKey().toLowerCase(), entry.getValue());
		}
	}

	/**
	 * Get all table names.
	 *
	 * @return all table names
	 */
	public Collection<String> getAllTableNames() {
		return tables.keySet();
	}

	/**
	 * Get table meta data via table name.
	 *
	 * @param tableName tableName table name
	 * @return table mata data
	 */
	public TableMetaData get(final String tableName) {
		return tables.get(tableName.toLowerCase());
	}

	/**
	 * Merge schema meta data.
	 *
	 * @param schemaMetaData schema meta data
	 */
	public void merge(final SchemaMetaData schemaMetaData) {
		tables.putAll(schemaMetaData.tables);
	}

	/**
	 * Add table meta data.
	 *
	 * @param tableName     table name
	 * @param tableMetaData table meta data
	 */
	public void put(final String tableName, final TableMetaData tableMetaData) {
		tables.put(tableName.toLowerCase(), tableMetaData);
	}

	/**
	 * Remove table meta data.
	 *
	 * @param tableName table name
	 */
	public void remove(final String tableName) {
		tables.remove(tableName.toLowerCase());
	}

	/**
	 * Judge contains table from table meta data or not.
	 *
	 * @param tableName table name
	 * @return contains table from table meta data or not
	 */
	public boolean containsTable(final String tableName) {
		return tables.containsKey(tableName.toLowerCase());
	}

	/**
	 * Judge whether contains column name.
	 *
	 * @param tableName  table name
	 * @param columnName column name
	 * @return contains column name or not
	 */
	public boolean containsColumn(final String tableName, final String columnName) {
		return containsTable(tableName) && get(tableName).getColumns().containsKey(columnName.toLowerCase());
	}

	/**
	 * Get all column names via table.
	 *
	 * @param tableName table name
	 * @return column names
	 */
	public List<String> getAllColumnNames(final String tableName) {
		return containsTable(tableName) ? new ArrayList<>(get(tableName).getColumns().keySet())
				: Collections.emptyList();
	}
}
