package io.devpl.codegen;

import io.devpl.codegen.mbpg.FastAutoGenerator;
import io.devpl.codegen.mbpg.config.OutputFile;
import io.devpl.codegen.mbpg.config.builder.Entity;
import io.devpl.codegen.mbpg.config.builder.Mapper;
import io.devpl.codegen.mbpg.config.rules.DateTimeType;
import io.devpl.codegen.mbpg.template.FreemarkerTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * 基于模板的代码生成
 * 整合mybatis generator和mybatis-plus generator
 * mybatis-plus generator 3.5.2版本
 * 官方文档：<a href="https://github.com/baomidou/generator">...</a>
 */
public class DevplCodeGenerator {

    private static final String author = "Author";
    /**
     * 输出根目录
     */
    private static final String outputRootDir = "D:\\Temp";
    // 父包配置
    private static final String parentPackage = "";
    // 要生成的表名列表
    private static final List<String> tableNamesToBeGenerated = new ArrayList<>();

    // 在此处填写要生成的表名
    private static void tableNamesToBeGenerated() {
        tableNamesToBeGenerated.add("user_group");
        tableNamesToBeGenerated.add("group_user");
        tableNamesToBeGenerated.add("group_role");
    }

    public static void main(String[] args) throws IOException {
        tableNamesToBeGenerated();
        URL resource = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties");
        if (resource == null) {
            throw new RuntimeException("数据库连接配置文件[resources/jdbc.properties]不存在");
        }
        Properties properties = new Properties();
        try (InputStream inputStream = resource.openStream()) {
            properties.load(inputStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
                    builder.author(author); // 设置作者名 baomidou 默认值:作者
                    builder.fileOverride();
                    // .enableSwagger() // 开启 swagger 模式
                    // .enableSpringdoc()  // 开启 springdoc 模式  @Schema注解
                    builder.dateType(DateTimeType.TIME_PACK);  // 时间策略
                    builder.commentDate("yyyy-MM-dd HH:mm:ss");// 注释日期 默认值: yyyy-MM-dd
                    builder.outputDir(outputRootDir); // 指定输出根目录 默认值: windows:D:// linux or mac : /tmp
                }).packageConfig(builder -> {
                    // 包配置(PackageConfig)
                    builder.parent(parentPackage); // 设置父包名
                    builder.moduleName(""); // 设置父包模块名
                    builder.entity("");  // Entity 包名
                    builder.service("");  // Service 包名

                    Map<OutputFile, String> pathInfoMap = new HashMap<>();
                    pathInfoMap.put(OutputFile.parent, outputRootDir);
                    pathInfoMap.put(OutputFile.xml, outputRootDir + "/mapping");
                    pathInfoMap.put(OutputFile.entity, outputRootDir + "/entity");
                    pathInfoMap.put(OutputFile.service, outputRootDir + "/service");
                    pathInfoMap.put(OutputFile.serviceImpl, outputRootDir + "/service/impl");
                    builder.pathInfo(pathInfoMap); // 设置mapperXml生成路径
                }).injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, stringObjectMap) -> {
                        // System.out.println(tableInfo);
                        // System.out.println(stringObjectMap);
                    });
                }).strategyConfig(builder -> {
                    // builder.enableSkipView(); // 开启大写命名
                    // builder.enableSchema(); // 启用 schema
                    builder.addTablePrefix("");
                    builder.addTableSuffix("");
                    builder.addInclude(tableNamesToBeGenerated); // 设置需要生成的表名
                    // Entity策略配置
                    Entity.Builder entityBuilder = builder.entityBuilder();
                    entityBuilder.enableFileOverride();  // 文件覆盖
                    entityBuilder.enableLombok();  // 使用Lombok
                    entityBuilder.enableTableFieldAnnotation(); // 字段添加TableField注解
                    entityBuilder.mapperBuilder();
                    entityBuilder.enableFileOverride();
                    // Mapper配置
                    Mapper.Builder mapperBuilder = entityBuilder.mapperBuilder();
                    mapperBuilder.enableBaseResultMap();
                    mapperBuilder.enableBaseResultMap(); // 生成默认的ResultMap标签
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
