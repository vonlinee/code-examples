package io.doraemon.pocket.generator.model.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ArrayResultSetHandler implements ResultSetHandler<Object[]>{
	@Override
	public Object[] handle(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		Object[] array = new Object[columnCount];
		while (rs.next()) {
            for (int i = 0; i < columnCount; i++) {
            	array[i] = rs.getObject(i + 1);
            }
        }
		return array;
	}
}
