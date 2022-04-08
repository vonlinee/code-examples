package sample.dynamic.datasource.mapper;


import sample.dynamic.datasource.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {
    @Select("SELECT * FROM product")
    List<Product> list();

    @Insert("INSERT INTO  product(`product_name`) VALUES (#{productName})")
    void save(Product product);
}