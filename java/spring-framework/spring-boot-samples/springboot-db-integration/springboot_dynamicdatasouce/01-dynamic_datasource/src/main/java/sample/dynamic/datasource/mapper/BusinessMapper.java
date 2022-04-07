package sample.dynamic.datasource.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BusinessMapper {
    @Select("SELECT * FROM t_order_item A LEFT JOIN t_product B ON A.prod_id = B.prod_id")
    public List<Map<String, Object>> multiJoinQuery();
}
