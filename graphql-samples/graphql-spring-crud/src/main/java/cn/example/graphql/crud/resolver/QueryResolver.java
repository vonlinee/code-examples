package cn.example.graphql.crud.resolver;

import cn.example.graphql.crud.entity.Category;
import cn.example.graphql.crud.entity.Product;
import cn.example.graphql.crud.input.InputInfo;
import cn.example.graphql.crud.repository.CategoryRepository;
import cn.example.graphql.crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public Category category(Integer id) {
        return categoryRepository.findById(id).orElseGet(null);
    }

    //映射关系
    public Category category(Integer id, InputInfo input) {
        System.out.println(input);
        return categoryRepository.findById(id).orElseGet(null);
    }

    public Product product(Integer id) {
        return productRepository.findById(id).orElseGet(null);
    }

}