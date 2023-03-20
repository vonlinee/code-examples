package io.devpl.toolkit;

import io.devpl.toolkit.mbp.NameConverter;

public class TestApplication {


    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql://localhost:3306/devpl")
                .userName("root")
                .password("123456")
                .port(8068)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .basePackage("com.github.davidfantasy.mybatisplus.generatorui.example")
                .nameConverter(new NameConverter() {
                    @Override
                    public String serviceNameConvert(String tableName) {
                        return this.entityNameConvert(tableName) + "Service";
                    }

                    @Override
                    public String controllerNameConvert(String tableName) {
                        return this.entityNameConvert(tableName) + "Action";
                    }
                })
                .build();
        MybatisPlusToolsApplication.run(config);
    }


}
