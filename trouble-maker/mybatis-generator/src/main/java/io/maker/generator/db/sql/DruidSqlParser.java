package io.maker.generator.db.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;

import java.util.List;

/**
 * https://github.com/alibaba/druid/wiki/SQL-Parser
 */
public class DruidSqlParser {

    public static void main(String[] args) {
        final DbType dbType = JdbcConstants.MYSQL; // 可以是ORACLE、POSTGRESQL、SQLSERVER、ODPS等
        String sql = "SELECT * FROM orders o LEFT JOIN orderitems o2 ON o.order_num = o2.order_num";

        System.out.println(execMySQL(sql));

        test1();
    }

    public static String execMySQL(String sql) {
        SchemaStatVisitor schemaStatVisitor = new SchemaStatVisitor();
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(statementList.size());
        for (SQLStatement statement : statementList) {
            statement.accept(schemaStatVisitor);
        }
        return "";
    }

    public static void test1() {
        String sql = "SELECT o.A, o.B FROM orders o LEFT JOIN orderitems o2 ON o.order_num = o2.order_num";
        DbType dbType = JdbcConstants.MYSQL;
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        SQLStatement stmt = stmtList.get(0);
        SchemaStatVisitor statVisitor = SQLUtils.createSchemaStatVisitor(dbType);
        stmt.accept(statVisitor);
        System.out.println(statVisitor.getColumns()); // [t_user.name, t_user.age, t_user.id]
        System.out.println(statVisitor.getTables()); // {t_user=Select}
        System.out.println(statVisitor.getConditions()); // [t_user.id = 1]
        System.out.println(statVisitor.getParameters());
    }
}
