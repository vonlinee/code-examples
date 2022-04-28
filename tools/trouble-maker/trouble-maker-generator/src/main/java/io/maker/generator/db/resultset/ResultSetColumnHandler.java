package io.maker.generator.db.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetColumnHandler implements ResultSetHandler<ResultSetColumn> {

    @Override
    public ResultSetColumn handle(ResultSet rs) throws SQLException {
        ResultSetColumn resultSetColumn = new ResultSetColumn();
        ResultSetMetaData rsmd = rs.getMetaData();
        try {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                resultSetColumn.addColumnMetadata(new ResultSetColumnMetadata(rsmd, i));
            }
            resultSetColumn.setData(new MapListHandler().handle(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetColumn;
    }
}
