package io.devpl.toolkit;

import io.devpl.toolkit.codegen.DefaultNameConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeGenApplication {

    private static final String URL = "jdbc:mysql://localhost:3306/devpl";

    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder()
                .jdbcUrl(URL)
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
        MybatisPlusToolsApplication.run(config);

        System.out.println("http://localhost:8068/");
    }
}
