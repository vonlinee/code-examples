package sample.dynamic.datasource.service;

import sample.dynamic.datasource.entity.Product;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface ProductService {
    List<Product> list();

    void save(Product frend);

}
