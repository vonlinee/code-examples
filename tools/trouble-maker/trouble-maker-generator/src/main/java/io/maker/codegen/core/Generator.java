package io.maker.codegen.core;

import java.sql.SQLException;

import io.maker.codegen.core.db.JdbcUtils;

public class Generator {
	
	public static void main(String[] args) throws SQLException {
		
		JdbcUtils.getConnection(null, null, 0, null, null);
		
		
	}
}
