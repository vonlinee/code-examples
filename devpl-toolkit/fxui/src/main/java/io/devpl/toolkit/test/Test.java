package io.devpl.toolkit.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.toolkit.fxui.utils.DBUtils;

public class Test {

	public static void main(String[] args) throws SQLException {

		Connection connection = DBUtils.getConnection(
				"jdbc:mysql://localhost:3306/devpl?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8",
				"root", "123456");
		
		printConnectionInfo(connection);

		Connection connection1 = DBUtils.getConnection(
				"jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8",
				"root", "123456");
		
		printConnectionInfo(connection1);
	}
	
	public static void printConnectionInfo(Connection connection) throws SQLException {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		DatabaseMetaData dbmd = connection.getMetaData();
		String userName = dbmd.getUserName();
		
		System.out.println(userName);
		System.out.println(dbmd.getCatalogTerm());
		System.out.println(dbmd.getSchemaTerm());
		System.out.println(dbmd.getDatabaseProductName());
		System.out.println(dbmd.getDriverName());
		System.out.println(dbmd.getProcedureTerm());
		
		ResultSet rs = dbmd.getCatalogs();

		System.out.println("==================== 所有表 START =========================");
		for (TableMetadata tablesMetadatum : DBUtils.getTablesMetadata(connection)) {
			System.out.println(tablesMetadatum.getTableName());
		}
		System.out.println("==================== 所有表 END =========================");
		// 获取所有数据库名
		System.out.println(DBUtils.extractOneColumn(String.class, rs));
		
		ResultSet schemas = dbmd.getSchemas();
		
		System.out.println(DBUtils.toMapList(schemas));
		System.out.println("============================");
	}
}
