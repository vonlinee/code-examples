package code.example.mybatis.crud.mapper;

import java.util.Map;

public interface TClassMapper {
    int deleteByPrimaryKey(String id);

    int insertOne(Map<String, Object> param);
}
