package sample.mybatis.jdbc;

import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.StatementImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import sample.mybatis.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.liaoxuefeng.com/wiki/1252599548343744/1322290857902113
 */
public class JdbcTest {

    static final String url = "jdbc:mysql://localhost:3306/mybatis_learn?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";

    @NotNull
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, "root", "123456");
        } catch (SQLException throwables) {
            System.out.println("获取连接失败, 退出进程");
            System.exit(0);
            return null;
        }
    }

    private void batchInsert(Connection conn, List<Student> studentList) {
        String sql = "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // 对同一个PreparedStatement反复设置参数并调用addBatch():
            for (Student s : studentList) {
                ps.setString(1, s.getName());
                ps.setBoolean(2, s.getGender());
                ps.setInt(3, s.getGrade());
                ps.setInt(4, s.getScore());
                ps.addBatch(); // 添加到batch
            }
            // 执行batch:
            int[] ns = ps.executeBatch();
            for (int n : ns) {
                System.out.println("insert " + n + " row"); // 每个batch中每个SQL执行的结果数量
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testBatch() throws SQLException {
        Connection conn = getConnection();
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student student = new Student("name" + i, i < 3, i, 90 + i);
            studentList.add(student);
        }
        batchInsert(conn, studentList);
    }

    @Test
    public void test1() throws SQLException {
        Connection connection = getConnection();
        System.out.println(connection);
        // JDBC4Connection
        JDBC4Connection jdbcConnection = (JDBC4Connection) connection;
        jdbcConnection.setAutoCommit(false);
        jdbcConnection.setAutoDeserialize(true);
        StatementImpl stmt = (StatementImpl) jdbcConnection.createStatement();
        System.out.println(stmt);
    }
}
