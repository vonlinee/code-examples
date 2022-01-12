package code.example.mybatis.mapper;

import java.util.List;

import code.example.mybatis.entity.Student;

public interface StudentMapper {
    List<Student> queryAll();
}
