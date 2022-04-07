package sample.dynamic.datasource.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Data
public class Product {
    private Long id;
    private String productName;

}