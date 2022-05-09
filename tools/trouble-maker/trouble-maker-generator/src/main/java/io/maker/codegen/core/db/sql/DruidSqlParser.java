package io.maker.codegen.core.db.sql;

import java.util.List;
import java.util.Map;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.druid.util.JdbcConstants;

/**
 * https://github.com/alibaba/druid/wiki/SQL-Parser
 * 
 * https://www.cnblogs.com/bigstrong/p/15190473.html  访问者模式
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
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, DbType.mysql);
        SQLStatement stmt = stmtList.get(0);
        for (int i = 0; i < stmtList.size(); i++) {
            SchemaStatVisitor statVisitor = SQLUtils.createSchemaStatVisitor(DbType.mysql);
            stmt.accept(statVisitor);
            System.out.println(statVisitor.getColumns()); // [t_user.name, t_user.age, t_user.id]
            System.out.println(statVisitor.getTables()); // {t_user=Select}
            System.out.println(statVisitor.getConditions()); // [t_user.id = 1]
            System.out.println(statVisitor.getParameters());
            
            Map<Name, TableStat> tables = statVisitor.getTables();
            
            TableStat tableStat = tables.get(new Name(""));
            tableStat.setInsertCount(3);
            
		}
    }
    
    public static void test2() {
        // 待解析 SQL
        String selectSql = "select * from order o left join order_item oi on o.order_num = oi.order_num";
        // 新建 Parser
        SQLStatementParser parser = new SQLStatementParser(selectSql, DbType.mysql);
        // 使用 Parser 解析 SQL，生成 AST
        SQLStatement sqlStatement = parser.parseStatement();
        // 生成访问者
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        // 使用 Visitor 访问 AST
        sqlStatement.accept(visitor);
        // 获取解析信息
        System.out.println(visitor.getColumns());
    }
}
