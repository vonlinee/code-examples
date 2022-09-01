package sample.mybatis.mapper;

import java.util.List;
import java.util.Map;

import sample.mybatis.entity.Teacher;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {

    int insertJson(@Param("param") Map<String, Object> date);

    int insertJson1(@Param("param") Map<String, Object> date);

    //MapKey用在返回值为Map时，可将对象转化为Map
    @MapKey("")
    List<Map<String, Object>> queryAll();
    
    List<Map<String, Object>> queryAllTeacher();
    
    // 不使用ResultMap
    List<Map<String, Object>> queryTeacherAll();

    List<Map<String, String>> queryTeacherAllBlob();

    int insertTeacher(@Param("teacher") Teacher teacher);
}
