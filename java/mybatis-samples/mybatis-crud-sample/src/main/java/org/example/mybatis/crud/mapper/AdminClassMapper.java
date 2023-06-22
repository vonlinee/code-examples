package org.example.mybatis.crud.mapper;

import org.example.mybatis.crud.entity.AdminClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminClassMapper {

    List<AdminClass> selectByCondition(@Param("className") String className, @Param("grade") String grade);
}
