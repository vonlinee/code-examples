package io.devpl.codegen.mbpg.jdbc.resultset;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityListRowHandler implements ResultSetExtractor<List<Map<String, Object>>> {

    private final MapEntityRowMapper rowMapper = new MapEntityRowMapper();

    @Override
    public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Map<String, Object>> rows = new ArrayList<>();
        int i = 0;
        while (rs.next()) {
            final Map<String, Object> map = rowMapper.mapRow(rs, i++);
            rows.add(map);
        }
        return rows;
    }
}
