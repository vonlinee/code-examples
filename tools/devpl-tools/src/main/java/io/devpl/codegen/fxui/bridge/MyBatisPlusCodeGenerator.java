package io.devpl.codegen.fxui.bridge;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 在3.5.2版本基础上进行修改
 * 官方文档：<a href="https://github.com/baomidou/generator">...</a>
 */
public class MyBatisPlusCodeGenerator {

    private static final String url = "jdbc:mysql://192.168.129.30:3306/lgdb_cloud_resource_management?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=GMT%2B8";
    private static final String username = "root";
    private static final String password = "LancooECP";
    private static final String author = "";
    private static final String outputDir = "D:\\temp";
    private static final String parentPackage = "test";
    // 要生成的表名列表
    private static final List<String> tableNamesToBeGenerated = new ArrayList<>();

    // 在此处填写要生成的表名
    private static void tableNamesToBeGenerated() {
        tableNamesToBeGenerated.add("resource_usage");
    }

    public static void main(String[] args) {
        tableNamesToBeGenerated();
        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
                             builder.author(author) // 设置作者名 baomidou 默认值:作者
                                    .fileOverride().enableSwagger() // 开启 swagger 模式
                                    .enableSpringdoc()  // 开启 springdoc 模式
                                    .dateType(DateType.TIME_PACK)  // 时间策略
                                    .commentDate("yyyy-MM-dd HH:mm:ss") // 注释日期 默认值: yyyy-MM-dd
                                    .outputDir(outputDir); // 指定输出目录 /opt/baomidou/ 默认值: windows:D:// linux or mac : /tmp
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
