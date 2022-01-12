package code.example.mybatis.crud.mapper;

import code.example.mybatis.crud.entity.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> queryAll();
}
