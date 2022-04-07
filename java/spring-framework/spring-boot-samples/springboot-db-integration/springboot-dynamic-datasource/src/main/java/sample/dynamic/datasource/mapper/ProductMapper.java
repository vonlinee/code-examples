package sample.dynamic.datasource.mapper;

import org.apache.ibatis.annotations.Select;
import sample.dynamic.datasource.entity.Product;

import java.util.List;

public interface ProductMapper {

    @Select("SELECT * FROM product")
    List<Product> queryAll();
}

