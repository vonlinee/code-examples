package com.baomidou.mybatisplus.generator.test;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.entity.BaseEntity;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

public class MySQLGenerator {

    public static void main(String[] args) {

        new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/db_mysql", "root", "123456")
            .dbQuery(new MySqlQuery())
            .schema("mybatis-plus")
            .typeConvert(new MySqlTypeConvert())
            .keyWordsHandler(new MySqlKeyWordsHandler())
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

        new StrategyConfig.Builder()
            .controllerBuilder()
            .superClass(Object.class)
            .enableHyphenStyle()
            .enableRestStyle()
            .formatFileName("%sAction")
            .build();

        new StrategyConfig.Builder()
            .serviceBuilder()
            .superServiceClass(Object.class)
            .superServiceImplClass(Object.class)
            .formatServiceFileName("%sService")
            .formatServiceImplFileName("%sServiceImp")
            .build();

        new StrategyConfig.Builder()
            .mapperBuilder()
            .superClass(Object.class)
            .enableMapperAnnotation()
            .enableBaseResultMap()
            .enableBaseColumnList()
            .formatMapperFileName("%sDao")
            .formatXmlFileName("%sXml")
            .build();

        FastAutoGenerator.create("url", "username", "password")
            .globalConfig(builder -> {
                builder.author("baomidou") // 设置作者
                    .enableSwagger() // 开启 swagger 模式
                    .fileOverride() // 覆盖已生成文件
                    .outputDir("D://"); // 指定输出目录
            })
            .packageConfig(builder -> {
                builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                    .moduleName("system") // 设置父包模块名
                    .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
                builder.addInclude("t_simple") // 设置需要生成的表名
                    .addTablePrefix("t_", "c_"); // 设置过滤表前缀
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }
}
