package org.example.mybatis.crud.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.mybatis.crud.entity.Student;
import org.example.mybatis.crud.param.ListParam;

import java.util.List;

public interface TStudentMapper {
    int deleteByPrimaryKey(String id);

    List<Student> selectList(@Param("param") ListParam param);
}
