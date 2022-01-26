package io.doraemon.pocket.generator.sql;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Table meta data loader.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TableMetaDataLoader {
    
    /**
     * Load table meta data.
     *
     * @param dataSource data source
     * @param table table name
     * @param databaseType database type
     * @return table meta data
     * @throws SQLException SQL exception
     */
    public static TableMetaData load(final DataSource dataSource, final String table, final String databaseType) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return new TableMetaData(ColumnMetaDataLoader.load(connection, table, databaseType), IndexMetaDataLoader.load(connection, table, databaseType));
        }
    }
}