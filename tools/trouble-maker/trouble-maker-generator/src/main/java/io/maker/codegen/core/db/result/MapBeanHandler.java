package io.maker.codegen.core.db.result;

import io.maker.base.lang.MapBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapBeanHandler implements ResultSetHandler<MapBean> {
    @Override
    public MapBean handle(ResultSet rs) throws SQLException {
        return null;
    }
}
