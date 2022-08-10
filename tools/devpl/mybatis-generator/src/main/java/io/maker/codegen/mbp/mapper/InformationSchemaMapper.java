package io.maker.codegen.mbp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.maker.codegen.mbp.entity.Columns;
import io.maker.codegen.mbp.entity.Tables;

public interface InformationSchemaMapper {

	public List<Columns> selectColumns(@Param("mapParam") Map<String, Object> params);
	
	public List<Tables> selectTables(@Param("mapParam") Map<String, Object> params);
}
