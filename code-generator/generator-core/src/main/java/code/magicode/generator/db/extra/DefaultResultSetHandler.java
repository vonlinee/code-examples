package code.magicode.generator.db.extra;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.maker.base.lang.NamedValue;

public class DefaultResultSetHandler implements ResultSetHandler<List<List<NamedValue>>> {

	/**
	 * List<List<NamedValue>> 等同于 List<Map<String, Object>>
	 * List<NamedValue> -> Row
	 */
    @Override
    public List<List<NamedValue>> handle(ResultSet rs) throws SQLException {
        List<List<NamedValue>> resultSetData = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        try {
            while (rs.next()) {
                List<NamedValue> row = new ArrayList<>(columnCount);
                for (int i = 0; i < columnCount; i++) {
                    //获取键名及值
                    row.add(new NamedValue(metaData.getColumnName(i + 1), rs.getObject(i + 1)));
                }
                resultSetData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetData;
    }
}