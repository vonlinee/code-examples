package code.example.jdbc.test;

import code.example.jdbc.utils.JdbcUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDTest {

    public static void main(String[] args) {

        // float
        String sql1 = "update course set time_duration = ? where course_id = '1'";
        // decimal (5, 2)
        String sql2 = "update course set col_decimal = ? where course_id = '1'";
        // double
        String sql3 = "update course set col_decimal = ? where course_id = '1'";

        try (Connection connection = JdbcUtils.getLocalMySQLConnection("mysql_learn")) {
            try (PreparedStatement ps = connection.prepareStatement(sql2)) {

                BigDecimal bigDecimal = BigDecimal.valueOf(Long.parseLong("0.1342"));
                ps.setBigDecimal(1, bigDecimal);
                int i = ps.executeUpdate();
                System.out.println(i);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
