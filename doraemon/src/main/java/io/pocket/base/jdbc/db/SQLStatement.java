package io.pocket.base.jdbc.db;

import java.util.List;

/**
 * 参考使用：https://github.com/exasol/sql-statement-builder/
 *
 * @author vonline
 */
public abstract class SQLStatement {
    // https://blog.csdn.net/weixin_41490593/article/details/94745433

    public abstract String render();

    String sql = "SELECT * FROM `information_schema`.`TABLES` T \r\n"
            + "	LEFT JOIN `information_schema`.`COLUMNS` C ON T.TABLE_NAME = C.TABLE_NAME \r\n"
            + "WHERE T.TABLE_NAME = 't_usc_mdm_user_dlr'\r\n" + "ORDER BY\r\n" + "    T.TABLE_NAME, C.ORDINAL_POSITION";

    public static class Select extends SQLStatement {

        private final List<String> tableNames;

        public Select(List<String> tableNames) {
            this.tableNames = tableNames;
        }

        public Select table(String tableName, String alias) {
            tableNames.add(tableName);
            return this;
        }

        @Override
        public String render() {
            return null;
        }
    }

    public static Select select() {
        return new Select(null);
    }
}
