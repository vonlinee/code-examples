package code.example.jdbc.transaction;

import code.example.jdbc.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction {

    @Test
    public void test1() throws SQLException {

        Connection connection = JdbcUtils.getLocalMySQLConnection("mybatis_learn");
        String sql = "";
        connection.setAutoCommit(false);

    }
}
