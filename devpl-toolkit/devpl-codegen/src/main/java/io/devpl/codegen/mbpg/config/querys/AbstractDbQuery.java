package io.devpl.codegen.mbpg.config.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.devpl.codegen.mbpg.config.IDbQuery;

/**
 * 表数据查询抽象类
 */
public abstract class AbstractDbQuery implements IDbQuery {

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }

    @Override
    public String[] fieldCustom() {
        return null;
    }
}
