package sample.mybatis.mapper;

import sample.mybatis.entity.Student;

import java.util.List;

public interface StudentMapper {

    int insertOne(Student student);

    int insertBatchWithForeach(List<Student> studentList);

    int insertBatchWithMultiQueries(List<Student> studentList);

    int deleteAll();
}
