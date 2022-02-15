package com.baomidou.mybatisplus.generator.jdbc.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Index meta data loader.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndexMetaDataLoader {

    private static final String INDEX_NAME = "INDEX_NAME";

    /**
     * Load column meta data list.
     * @param connection connection
     * @param table table name
     * @param databaseType databaseType
     * @return index meta data list
     * @throws SQLException SQL exception
     */
    public static Collection<IndexMetaData> load(final Connection connection, final String table, final String databaseType) throws SQLException {
        Collection<IndexMetaData> result = new HashSet<>();
        try (ResultSet resultSet = connection.getMetaData().getIndexInfo(connection.getCatalog(), JdbcUtil.getSchema(connection, databaseType), table, false, false)) {
            while (resultSet.next()) {
                String indexName = resultSet.getString(INDEX_NAME);
                if (null != indexName) {
                    result.add(new IndexMetaData(indexName));
                }
            }
        }
        return result;
    }
}
