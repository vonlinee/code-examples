package io.spring.boot.mapper;

import java.util.List;

import io.spring.boot.common.mybatis.MyMapper;
import io.spring.boot.common.entity.Student;

/**
 * StudentMapper，映射SQL语句的接口，无逻辑实现
 */
public interface StudentMapper extends MyMapper<Student> {

    List<Student> likeName(String name);

    Student getById(int id);

    int add(Student stu);

    String getNameById(int id);
}
