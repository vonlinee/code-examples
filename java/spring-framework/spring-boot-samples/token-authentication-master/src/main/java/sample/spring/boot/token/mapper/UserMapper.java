package sample.spring.boot.token.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface UserMapper {
    Map<String, Object> getUser(@Param("param") Map<String, Object> map);
}
