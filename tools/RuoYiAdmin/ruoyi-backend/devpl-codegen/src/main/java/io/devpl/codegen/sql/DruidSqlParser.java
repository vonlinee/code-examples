package io.devpl.codegen.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLIndex;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCharacterDataType;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;

import java.util.List;
import java.util.function.Function;

/**
 * 只针对MySQL
 */
public class DruidSqlParser {

    private final String rawSql;
    private final DbType dbType;
    private List<SQLStatement> statemens;

    /**
     * 解析单条sql
     * @param sql    sql
     * @param dbType 数据库类型
     */
    public DruidSqlParser(String sql, DbType dbType) {
        this.rawSql = sql;
        this.dbType = dbType;
        this.statemens = SQLUtils.parseStatements(rawSql, dbType);
    }


    public void parseCreate() {
        for (SQLStatement sqlStatement : statemens) {
            MySqlCreateTableStatement ddlCreate = (MySqlCreateTableStatement) sqlStatement;
            List<SQLColumnDefinition> columnDefinitions = ddlCreate.getColumnDefinitions();

            for (SQLColumnDefinition columnDefinition : columnDefinitions) {
                String columnName = columnDefinition.getColumnName();
                System.out.println(columnDefinition.getDefaultExpr());
            }

            SQLColumnDefinition newColDef = new SQLColumnDefinition();
            newColDef.setName("create_time");
            newColDef.setComment("创建时间");
            final SQLCharacterDataType dataType = new SQLCharacterDataType("VARCHAR");
            newColDef.setDataType(dataType);

            final List<MySqlKey> mysqlKeys = ddlCreate.getMysqlKeys();

            List<SQLTableElement> tableElementList = ddlCreate.getTableElementList();
        }
    }

    public void sort(List<SQLTableElement> tableElementList) {
        for (SQLTableElement element : tableElementList) {
            if (element instanceof SQLIndex) {

            }
        }
    }

    public String getRawSql() {
        return rawSql;
    }

    public DbType getDbType() {
        return dbType;
    }
}
