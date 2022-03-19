package sample.spring.integration.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MultiDataSourceMapper {
	public List<Map<String, Object>> queryFromMultiDataSource();
}
