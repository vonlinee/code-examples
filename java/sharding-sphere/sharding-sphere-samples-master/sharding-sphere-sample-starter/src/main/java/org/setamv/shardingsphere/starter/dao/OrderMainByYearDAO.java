package org.setamv.shardingsphere.starter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.setamv.shardingsphere.starter.model.OrderMainByYear;

@Mapper
public interface OrderMainByYearDAO {

    int insert(OrderMainByYear data);

    @Select("select * from order_main_by_year where order_id = #{orderId}")
    OrderMainByYear get(Long orderId);
}
