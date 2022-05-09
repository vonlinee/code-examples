package io.maker.codegen.utils;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SqlMapper {
    List<Map<String, Object>> selectAll(@Param("param") Map<String, Object> param);
}
