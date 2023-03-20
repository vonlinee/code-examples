package io.devpl.toolkit;

import io.devpl.toolkit.codegen.DefaultNameConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevplMain8068 {

    public static void main(String[] args) {
        MybatisPlusToolsApplication.generatorConfig = GeneratorConfig.builder()
                .jdbcUrl("jdbc:mysql://localhost:3306/devpl")
                .userName("root")
                .password("123456")
                .driverClassName(JdbcConstant.MYSQL8_DRIVER_CLASS_NAME)
                // 数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
                .schemaName("devpl")
                // 如果需要修改各类生成文件的默认命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法：
                .nameConverter(new DefaultNameConverter())
                .basePackage("com.xxx.example")
                .port(8068)
                .build();
        try {
            SpringApplication.run(DevplMain8068.class, args);
            System.out.println("http://localhost:8068/");
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }
}
