package io.devpl.codegen;

import io.devpl.codegen.mbpg.FastAutoGenerator;
import io.devpl.codegen.mbpg.config.OutputFile;
import io.devpl.codegen.mbpg.config.rules.DateType;
import io.devpl.codegen.mbpg.template.FreemarkerTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 基于模板的代码生成
 * 整合mybatis generator和mybatis-plus generator
 * mybatis-plus generator 3.5.2版本
 * 官方文档：<a href="https://github.com/baomidou/generator">...</a>
 */
public class DevplCodeGenerator {

    private static final String author = "Author";
    private static final String outputRootDir = "D:\\Temp";
    // 父包配置
    private static final String parentPackage = "";
    // 要生成的表名列表
    private static final List<String> tableNamesToBeGenerated = new ArrayList<>();

    // 在此处填写要生成的表名
    private static void tableNamesToBeGenerated() {
        tableNamesToBeGenerated.add("sys_post");
    }

    public static void main(String[] args) throws IOException {
        tableNamesToBeGenerated();
        URL resource = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties");
        if (resource == null) {
            return;
        }
        Properties properties = new Properties();
        try (InputStream inputStream = resource.openStream()) {
            properties.load(inputStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        final String url = properties.getProperty("jdbc.url");
        final String username = properties.getProperty("jdbc.username");
        final String password = properties.getProperty("jdbc.password");
        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
                    builder.author(author) // 设置作者名 baomidou 默认值:作者
                            .fileOverride()
                            .enableSwagger() // 开启 swagger 模式
                            // .enableSpringdoc()  // 开启 springdoc 模式  @Schema注解
                            .dateType(DateType.TIME_PACK)  // 时间策略
                            .commentDate("yyyy-MM-dd HH:mm:ss") // 注释日期 默认值: yyyy-MM-dd
                            .outputDir(outputRootDir); // 指定输出根目录 默认值: windows:D:// linux or mac : /tmp
                }).packageConfig(builder -> {
                    // 包配置(PackageConfig)
                    builder.parent(parentPackage) // 设置父包名
                            .moduleName("src.main.java") // 设置父包模块名
                            // .entity("") // Entity 包名
                            // .service("") // Service 包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Temp")); // 设置mapperXml生成路径
                }).injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, stringObjectMap) -> {
                        // System.out.println(tableInfo);
                        // System.out.println(stringObjectMap);
                    });
                }).strategyConfig(builder -> {
                    // builder
                    // .enableSkipView() // 开启大写命名
                    //.enableSchema(); // 启用 schema
                    builder
                            .addTablePrefix("")
                            .addTableSuffix("")
                            .addInclude(tableNamesToBeGenerated) // 设置需要生成的表名
                            // Entity策略配置
                            .entityBuilder()
                            .enableFileOverride()  // 文件覆盖
                            .enableLombok()   // 使用Lombok
                            .enableTableFieldAnnotation()  // 字段添加TableField注解
                            .mapperBuilder()
                            .enableFileOverride()
                            .enableBaseResultMap()
                            .mapperBuilder()
                            .enableBaseResultMap(); // 生成默认的ResultMap标签
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
