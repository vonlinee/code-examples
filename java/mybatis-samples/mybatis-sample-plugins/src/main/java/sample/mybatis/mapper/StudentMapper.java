package sample.mybatis.mapper;

import java.util.List;

import sample.mybatis.entity.Student;

public interface StudentMapper {
	List<Student> queryAll();
}
