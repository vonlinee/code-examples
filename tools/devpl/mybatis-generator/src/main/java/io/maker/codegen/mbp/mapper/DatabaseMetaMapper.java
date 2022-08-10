package io.maker.codegen.mbp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DatabaseMetaMapper {

	public List<Map<String, Object>> showTableStatus(@Param("mapParam") Map<String, Object> params);

	public List<Map<String, Object>> querySysTableIndex(@Param("mapParam") Map<String, Object> params);

	public List<Map<String, Object>> queryColumnMetaInformation(@Param("mapParam") Map<String, Object> params);
}
