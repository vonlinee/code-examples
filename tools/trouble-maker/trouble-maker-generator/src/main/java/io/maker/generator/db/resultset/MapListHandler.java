package io.maker.generator.db.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapListHandler implements ResultSetHandler<List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
		List<Map<String, Object>> resultSetDataList = new ArrayList<>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		try {
			while (rs.next()) {
				Map<String, Object> row = new HashMap<>(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object columnValue = rs.getObject(i);
					if (rs.wasNull()) {
						row.put(columnName, null);
					}
					row.put(columnName, columnValue);
				}
				resultSetDataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSetDataList;
	}
}
