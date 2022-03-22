package sample.spring.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataJpaHibernateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaHibernateApplication.class, args);
    }
}

//自动创建数据库，添加参数：createDatabaseIfNotExist=true
//spring.datasource.url=jdbc:mysql://localhost:3306/dbname?createDatabaseIfNotExist=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false


