package io.maker.generator.mbp.samples;

import java.util.Collections;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import io.maker.generator.mbp.FastAutoGenerator;
import io.maker.generator.mbp.config.*;
import io.maker.generator.mbp.config.converts.MySqlTypeConvert;
import io.maker.generator.mbp.config.po.LikeTable;
import io.maker.generator.mbp.config.querys.MySqlQuery;
import io.maker.generator.mbp.config.rules.DateType;
import io.maker.generator.mbp.config.rules.NamingStrategy;
import io.maker.generator.mbp.engine.FreemarkerTemplateEngine;
import io.maker.generator.mbp.entity.BaseEntity;
import io.maker.generator.mbp.fill.Column;
import io.maker.generator.mbp.fill.Property;
import io.maker.generator.mbp.keywords.MySqlKeyWordsHandler;

/**
 * https://github.com/baomidou/generator
 */
public class MySQLGenerator {

    public static final String url = "jdbc:mysql://localhost:3306/mysql_learn?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";

    public static final String OUTPUT_ROOT_DIR = "D://Temp";

    public static void main(String[] args) {

        new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus", "root", "123456")
                .dbQuery(new MySqlQuery()) // 数据库查询
                .schema("mybatis-plus") // 数据库schema(部分数据库适用)
                .typeConvert(new MySqlTypeConvert()) // 数据库类型转换器
                .keyWordsHandler(new MySqlKeyWordsHandler()) // 数据库关键字处理器
                .build();

        new GlobalConfig.Builder()
                .fileOverride()
                .outputDir("/opt/baomidou")
                .author("baomidou")
                .enableKotlin()
                .enableSwagger()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .build();

        new PackageConfig.Builder()
                .parent("com.baomidou.mybatisplus.samples.generator")
                .moduleName("sys")
                .entity("po")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .other("other")
                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://"))
                .build();

        new TemplateConfig.Builder()
                .disable(TemplateType.ENTITY)
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .xml("/templates/mapper.xml")
                .controller("/templates/controller.java")
                .build();

        new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                })
                .customMap(Collections.singletonMap("test", "baomidou"))
                .customFile(Collections.singletonMap("test.txt", "/templates/test.vm"))
                .build();

        new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .likeTable(new LikeTable("USER"))
                .addInclude("t_simple")
                .addTablePrefix("t_", "c_")
                .addFieldSuffix("_flag")
                .build();

        new StrategyConfig.Builder()
                .entityBuilder()
                .superClass(BaseEntity.class)
                .disableSerialVersionUID()
                .enableChainModel()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
                .enableActiveRecord()
                .versionColumnName("version")
                .versionPropertyName("version")
                .logicDeleteColumnName("deleted")
                .logicDeletePropertyName("deleteFlag")
                .naming(NamingStrategy.no_change)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                .addIgnoreColumns("age")
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
                .formatFileName("%sEntity")
                .build();

        // new StrategyConfig.Builder()
        //         .controllerBuilder()
        //         .superClass(BaseController.class)
        //         .enableHyphenStyle()
        //         .enableRestStyle()
        //         .formatFileName("%sAction")
        //         .build();

        FastAutoGenerator.create(url, "root", "123456").globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(OUTPUT_ROOT_DIR); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
                }).strategyConfig(builder -> {
                    builder.addInclude("orders") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
