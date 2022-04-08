package sample.dynamic.datasource.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.dynamic.datasource.entity.Product;
import sample.dynamic.datasource.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("frend")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService frendService;

    @GetMapping(value = "select")
    public List<Product> select() {
        return frendService.list();
    }

    @GetMapping(value = "insert")
    public void in() {
        Product frend = new Product();
        frend.setProductName("Product" + new Random(1).nextInt());
        frendService.save(frend);
    }
}
