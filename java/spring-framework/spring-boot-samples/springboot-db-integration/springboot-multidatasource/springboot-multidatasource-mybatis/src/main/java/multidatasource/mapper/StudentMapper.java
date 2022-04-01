package multidatasource.mapper;

import multidatasource.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> likeName(String name);
}
