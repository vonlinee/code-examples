package cn.example.graphql.crud.repository;

import cn.example.graphql.crud.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}