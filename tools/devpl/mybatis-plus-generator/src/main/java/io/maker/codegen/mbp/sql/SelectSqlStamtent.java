package io.maker.codegen.mbp.sql;

import java.util.Map;

public class SelectSqlStamtent extends SqlStatement {

    private String firstTableName;

    @Override
    public String getSql() {
        return null;
    }

    public static class Builder implements SqlStatement.Builder<SelectSqlStamtent> {

        /**
         * 存放查询的列和别名
         */
        private Map<String, String> columnNameAndAlias;

        public Builder from(String tableName, String tableAlias) {
            return this;
        }

        public Builder columns(String... columns) {
            return this;
        }

        @Override
        public SqlStatement build() {
            return null;
        }
    }
}