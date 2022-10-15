package com.zzg.mybatis.generator.util;

import io.devpl.codegen.common.utils.ConfigHelper;
import io.devpl.codegen.common.utils.ConnectionManager;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by zouzhigang on 2016/9/18.
 */
public class DbTest {

    @Test
    public void testFindConnectorLibPath_Oracle() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("show databases");

    }

    @Test
    public void testFindConnectorLibPath_Mysql() {
        String path = ConfigHelper.findConnectorLibPath("MySQL");
        Assert.assertTrue(path.contains("mysql-connector"));
    }

    @Test
    public void testFindConnectorLibPath_PostgreSQL() {
        String path = ConfigHelper.findConnectorLibPath("PostgreSQL");
        Assert.assertTrue(path.contains("postgresql"));
    }

    @Test
	public void testGetAllJDBCDriverJarPaths() {
    	List<String> jarFilePaths = ConfigHelper.getAllJDBCDriverJarPaths();
    	Assert.assertTrue(jarFilePaths != null && jarFilePaths.size() > 0);
    }
}
