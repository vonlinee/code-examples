package code.sample.mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

//https://blog.csdn.net/chai_cmf/article/details/119296448
// https://blog.csdn.net/D102601560/article/details/110739667
//<!-- Swagger API文档 -->
// <dependency>
//     <groupId>io.springfox</groupId>
//     <artifactId>springfox-boot-starter</artifactId>
//     <version>3.0.0</version>
// </dependency>