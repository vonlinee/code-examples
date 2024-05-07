package code.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import code.example.mybatis.entity.Student;

public interface StudentMapper {
    List<Student> queryAll();
    Student queryByStuId(@Param("stuId") String stuId);
    Student queryByStuNo(@Param("stuNo") String stuNo);
    Student queryByStuNoUseCache(@Param("stuNo") String stuNo);

    int updateById();
}
