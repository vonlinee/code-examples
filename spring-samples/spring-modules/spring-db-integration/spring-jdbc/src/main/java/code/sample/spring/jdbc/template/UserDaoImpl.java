package code.sample.spring.jdbc.template;

import java.util.List;

import code.sample.spring.jdbc.template.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import code.sample.spring.jdbc.dao.UserDao;
import code.sample.spring.jdbc.model.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate template;

    public void displayData() {
        String sql = "select * from STUDENTS_T";
        List<Student> students = template.query(sql, new StudentMapper());
        for (Student student : students)
            System.out.println("ID : " + student.getId() + "\tNAME : " + student.getName());
    }

    public void createTeacher() {
        String sql = "CREATE TABLE t_eacher(ID INTEGER, TEACHER_NAME VARCHAR(40))";
        template.execute(sql);
        // execute() is used for DDL queries.
    }

    public void insertStudent(Student student) {
        String sql = "INSERT INTO STUDENTS_T(STUDENT_ID, STUDENT_NAME) VALUES(?, ?)";
        template.update(sql, new Object[]{student.getId(), student.getName()});
        // update() method works fine for insert/delete/update statements. and can also be used with stored procedure.
    }
}
