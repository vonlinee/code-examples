package io.maker.codegen.mbp.jdbc;

import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.maker.codegen.mbp.config.DataSourceConfig;

import java.sql.SQLException;
import java.util.Map;

public class DatabaseMetaDataWrapperTest {

    @Test
    void test() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:h2:mem:test;MODE=mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "sa", "").build();
        DatabaseMetaDataLoader databaseMetaDataWrapper = new DatabaseMetaDataLoader(dataSourceConfig.getConn());
        Map<String, DatabaseMetaDataLoader.ColumnsInfo> columnsInfo = databaseMetaDataWrapper.getColumnsInfo(null, null, "USERS");
        Assertions.assertNotNull(columnsInfo);
        DatabaseMetaDataLoader.ColumnsInfo name = columnsInfo.get("name");
        Assertions.assertTrue(name.isNullable());
        Assertions.assertEquals(JdbcType.VARCHAR, name.getJdbcType());
        Assertions.assertEquals(Integer.MAX_VALUE, name.getLength());
    }

}
