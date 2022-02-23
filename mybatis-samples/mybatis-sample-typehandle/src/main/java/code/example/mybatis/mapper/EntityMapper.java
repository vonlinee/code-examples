package code.example.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface EntityMapper {
    int insertOne(@Param("param") String date);

    List<Map<String, Object>> queryAll();

    List<Map<String, Object>> queryTeacherAll();

    List<Map<String, String>> queryTeacherAllBlob();
}
