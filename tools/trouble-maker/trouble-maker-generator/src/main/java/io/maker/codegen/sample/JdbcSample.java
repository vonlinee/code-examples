package io.maker.codegen.sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import io.maker.codegen.core.db.JdbcUtils;

public class JdbcSample {

	
	public static void main(String[] args) throws Exception {
		
		
		Connection connection = JdbcUtils.getLocalMySQLConnection("mysql_learn");
		
		Map<String, Object> map = JdbcUtils.getTableMetaData("orderitems", connection);
		
		System.out.println(map);
		
	}
}
