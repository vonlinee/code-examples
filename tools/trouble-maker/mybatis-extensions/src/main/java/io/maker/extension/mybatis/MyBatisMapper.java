package io.maker.extension.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MyBatisMapper {

	List<Map<String, Object>> showTableStatus(@Param("mapParam") Map<String, Object> paramMap);

	List<Map<String, Object>> queryColumnMetaInformation(@Param("mapParam") Map<String, Object> paramMap);

	List<Map<String, Object>> querySysTableIndex(@Param("mapParam") Map<String, Object> paramMap);
}
