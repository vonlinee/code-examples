package io.devpl.codegen;

import io.devpl.codegen.mbpg.FastAutoGenerator;
import io.devpl.codegen.mbpg.config.OutputFile;
import io.devpl.codegen.mbpg.config.rules.DateType;
import io.devpl.codegen.mbpg.template.FreemarkerTemplateEngine;
import io.devpl.codegen.mbpg.util.RuntimeUtils;
import io.devpl.sdk.util.PropertiesUtils;

import java.io.IOException;
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

    // 配置从本地文件加载
    private static final String JDBC_CONFIG_FILE = "D:/Temp/devpl-jdbc.properties";

    private static final String author = "someone";
    private static final String outputRootDir = "D:\\temp";
    // 父包配置
    private static final String parentPackage = "";
    // 要生成的表名列表
    private static final List<String> tableNamesToBeGenerated = new ArrayList<>();

    // 在此处填写要生成的表名
    private static void tableNamesToBeGenerated() {
        tableNamesToBeGenerated.add("dbs");
    }

    public static void main(String[] args) throws IOException {
//        Process process = RuntimeUtils.openTextFile(JDBC_CONFIG_FILE);
//        while (process.isAlive()) {
//            // 等待编辑配置结束
//        }
        tableNamesToBeGenerated();
        final Properties properties = PropertiesUtils.loadProperties(JDBC_CONFIG_FILE);
        final String url = properties.getProperty("url");
        final String username = properties.getProperty("username");
        final String password = properties.getProperty("password");
        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
                    builder.author(author) // 设置作者名 baomidou 默认值:作者
                            .fileOverride()
                            // .enableSwagger() // 开启 swagger 模式
                            // .enableSpringdoc()  // 开启 springdoc 模式  @Schema注解
                            .dateType(DateType.TIME_PACK)  // 时间策略
                            .commentDate("yyyy-MM-dd HH:mm:ss") // 注释日期 默认值: yyyy-MM-dd
                            .outputDir(outputRootDir); // 指定输出根目录 默认值: windows:D:// linux or mac : /tmp
                }).packageConfig(builder -> {
                    // 包配置(PackageConfig)
                    builder.parent(parentPackage) // 设置父包名
                            // .moduleName("") // 设置父包模块名
                            // .entity("") // Entity 包名
                            // .service("") // Service 包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Temp")); // 设置mapperXml生成路径
                }).strategyConfig(builder -> {
                    // builder
                    // .enableSkipView() // 开启大写命名
                    //.enableSchema(); // 启用 schema
                    builder.addTablePrefix("")
                            .addTableSuffix("")
                            .addInclude(tableNamesToBeGenerated) // 设置需要生成的表名
                            // Entity策略配置
                            .entityBuilder()
                            .enableFileOverride()
                            .enableLombok()
                            .mapperBuilder()
                            .enableFileOverride()
                            .enableBaseResultMap()
                            .mapperBuilder()
                            .enableBaseResultMap(); // 生成默认的ResultMap标签
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
