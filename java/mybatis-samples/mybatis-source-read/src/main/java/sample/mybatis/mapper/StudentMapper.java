package sample.mybatis.mapper;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import sample.mybatis.entity.Student;

import java.util.List;

public interface StudentMapper {

    void selectByCondition(RowBounds rowBounds, ResultHandler<Student> handler);

    int insertOne(Student student);

    int insertBatchWithForeach(List<Student> studentList);

    int insertBatchWithMultiQueries(List<Student> studentList);

    int deleteAll();
}
