package code.sample.spring.jdbc.template;

import code.sample.spring.jdbc.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt(1));    // student.setId(rs.getInt("STUDENT_ID"));
        student.setName(rs.getString(2));    // student.setName(rs.getString("STUDENT_NAME"));
        return student;
    }
}