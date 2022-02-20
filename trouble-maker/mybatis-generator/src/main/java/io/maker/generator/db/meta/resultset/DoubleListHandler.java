package io.maker.generator.db.meta.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List<List<Object>>
 * 第一行放表头
 * 后续的行放数据
 */
public class DoubleListHandler implements ResultSetHandler<List<List<Object>>> {

    @Override
    public List<List<Object>> handle(ResultSet rs) throws SQLException {
        List<List<Object>> resultSetDataList = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        ArrayList<Object> titles = new ArrayList<>(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            titles.add(rsmd.getColumnName(i));
        }
        resultSetDataList.add(titles);
        try {
            while (rs.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                resultSetDataList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetDataList;
    }
}
