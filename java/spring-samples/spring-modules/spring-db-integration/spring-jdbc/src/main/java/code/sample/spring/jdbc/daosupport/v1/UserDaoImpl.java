package code.sample.spring.jdbc.daosupport.v1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import code.sample.spring.jdbc.model.Student;
import lombok.Getter;
import lombok.Setter;

/**
 * JdbcDaoSupport是Spring内置的一个Dao层的基类，
 * 来自spring-jdbc-xxx.RELEASE.jar这个包，其内部定义了JdbcTemplate的set方法，
 * 这样我们自己的dao类只需要继承JdbcDaoSupport类，就可以省略JdbcTemplate的set方法书写了，
 * 通过查看源码你会发现，该方法是final修饰的
 */
public class UserDaoImpl extends JdbcDaoSupport {

    public void displayData() {
        String sql = "select * from STUDENTS_T";
        List<Student> students = getJdbcTemplate().query(sql, new StudentMapper());
        for (Student student : students)
            System.out.println("ID : " + student.getId() + "\tNAME : " + student.getName());
    }

    private static final class StudentMapper implements RowMapper<Student> {
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt(1)); // student.setId(rs.getInt("STUDENT_ID"));
            student.setName(rs.getString(2)); // student.setName(rs.getString("STUDENT_NAME"));
            return student;
        }
    }
}
