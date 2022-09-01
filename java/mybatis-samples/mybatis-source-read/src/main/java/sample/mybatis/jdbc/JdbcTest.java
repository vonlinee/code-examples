package sample.mybatis.jdbc;

import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.StatementImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import sample.mybatis.entity.Student;

import javax.accessibility.AccessibleAction;
import java.security.AccessController;
import java.security.PrivilegedAction;
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
            long start = System.currentTimeMillis();
            for (Student s : studentList) {
                ps.setString(1, s.getName());
                ps.setBoolean(2, s.getGender());
                ps.setInt(3, s.getGrade());
                ps.setInt(4, s.getScore());
                ps.addBatch(); // 添加到batch
            }
            // 执行batch:
            int[] ns = ps.executeBatch();
            long end = System.currentTimeMillis();
            for (int n : ns) {
                // System.out.println("insert " + n + " row"); // 每个batch中每个SQL执行的结果数量
            }
            System.out.println("插入" + ns.length + "行，耗时" + (end - start) + "ms");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testBatch() throws SQLException {
        batchInsert(getConnection(), prepareBatchStudents(100000));
    }

    public List<Student> prepareBatchStudents(int count) {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Student student = new Student("name" + i, i < 3, i, 90 + i);
            studentList.add(student);
        }
        return studentList;
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

        jdbcConnection.rollback();
    }
}
