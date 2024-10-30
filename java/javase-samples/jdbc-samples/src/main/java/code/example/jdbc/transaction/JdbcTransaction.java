package code.example.jdbc.transaction;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTransaction {

    QueryRunner runner = new QueryRunner(createDataSource());

    public static DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql_learn?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Test
    public void test1() throws SQLException {

        try (Connection connection = runner.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            Integer res = runner.query("SELECT COUNT(c1) FROM t1 WHERE c1 = 'xyz'", new ResultSetHandler<Integer>() {
                @Override
                public Integer handle(ResultSet rs) throws SQLException {
                    return rs.getInt("COUNT(c1)");
                }
            });
            System.out.println(res);
        }
    }
}
