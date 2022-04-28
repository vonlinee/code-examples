package io.maker.extension.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface MyBatisMapper {

	List<Map<String, Object>> select();
}
