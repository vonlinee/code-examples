package sample.spring.boot.token.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    Map<String, Object> getUser(@Param("param") Map<String, Object> map);
}
