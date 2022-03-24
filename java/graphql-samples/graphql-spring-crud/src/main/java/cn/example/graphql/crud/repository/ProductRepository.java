package cn.example.graphql.crud.repository;

import cn.example.graphql.crud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

}