package org.example.agentdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.agentdemo.entity.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> queryAll(@Param("param") Map<String, Object> param);
}
