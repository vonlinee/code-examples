package com.baomidou.mybatisplus.sample;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.maker.base.utils.Lists;

import java.sql.SQLException;
import java.util.Collections;

/**
 * <p>
 * 快速生成
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-16
 */
public class MySQLGenerator {

    public static final String URL = "jdbc:mysql://localhost:3306/ordercenter?characterEncoding=utf-8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    public static final String AUTHOR = "ly-busicen";

    public static final String OUTPUT_DIR = "D://Temp";
    public static final String MAPPER_OUT_ROOT_DIR = "D://Temp";
    public static final String PARENT_PACKAGE = "mbpgenerator";
    public static final String MODULE_NAME = "system";


    private static String url(String ip, String dbName) {
        return "jdbc:mysql://" + ip + ":3306/" + dbName + "?characterEncoding=utf-8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8";
    }

    /**
     * 执行
     */
    public static void main(String[] args) throws SQLException {

        String includeTables = String.join(",", Lists.of("tab_order"));

        FastAutoGenerator.create(URL, USERNAME, PASSWORD).globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(OUTPUT_DIR); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent(PARENT_PACKAGE) // 设置父包名
                            .moduleName(MODULE_NAME) // 设置父包模块名
                            .xml("mybatis.mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_OUT_ROOT_DIR)); // 设置mapperXml生成路径
                }).strategyConfig(builder -> {
                    builder.addInclude(includeTables) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")// 设置过滤表前缀
                            .addFieldPrefix("")
                            .addFieldSuffix("")
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList();
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}