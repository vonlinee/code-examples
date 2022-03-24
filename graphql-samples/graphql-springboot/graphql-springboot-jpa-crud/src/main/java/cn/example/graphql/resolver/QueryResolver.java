package cn.example.graphql.resolver;

import cn.example.graphql.entity.Category;
import cn.example.graphql.entity.Product;
import cn.example.graphql.repository.CategoryRepository;
import cn.example.graphql.repository.ProductRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Product product(Integer id) {
        return productRepository.findById(id).orElseGet(null);
    }

}
