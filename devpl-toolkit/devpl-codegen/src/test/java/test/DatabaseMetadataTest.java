package test;

import io.devpl.codegen.mbpg.jdbc.DBUtils;
import io.devpl.codegen.mbpg.jdbc.meta.PrimaryKey;
import io.devpl.codegen.mbpg.jdbc.resultset.EntityListRowHandler;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;

public class DatabaseMetadataTest {

    static final String URL = "jdbc:mysql://localhost:3306/lgdb_campus_intelligent_portrait?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=GMT%2B8";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "root", "123456");
    }

    @Test
    public void test1() throws SQLException {

        final Connection connection = getConnection();
        final String catalog = connection.getCatalog();
        final String schema = connection.getSchema();
        final DatabaseMetaData metaData = connection.getMetaData();

        final ResultSet rs = metaData.getPrimaryKeys(catalog, schema, "base_school_info");

        final List<PrimaryKey> results = DBUtils.extract(rs, PrimaryKey.class);

    }
}
