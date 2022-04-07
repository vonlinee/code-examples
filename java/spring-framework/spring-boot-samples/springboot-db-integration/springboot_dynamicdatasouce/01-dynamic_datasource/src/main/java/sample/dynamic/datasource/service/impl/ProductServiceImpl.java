package sample.dynamic.datasource.service.impl;

import sample.dynamic.datasource.annotation.WR;
import sample.dynamic.datasource.entity.Product;
import sample.dynamic.datasource.mapper.ProductMapper;
import sample.dynamic.datasource.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    @WR("R")        // 库2
    public List<Product> list() {
        return productMapper.list();
    }

    @Override
    @WR("W")        // 库1
    public void save(Product frend) {
        productMapper.save(frend);
    }
}
