package io.maker.generator.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.maker.generator.mybatis.entity.Columns;
import io.maker.generator.mybatis.entity.Tables;

public interface InformationSchemaMapper {

	List<Columns> selectColumns(@Param("mapParam") Map<String, Object> params);
	
	List<Tables> selectTables(@Param("mapParam") Map<String, Object> params);
}
