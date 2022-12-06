package io.devpl.codegen.fxui;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

/**
 * https://github.com/baomidou/generator
 */
public class MySQLGenerator {

    // 数据库连接信息配置
    public static final String URL1 = "jdbc:mysql://localhost:3306/information_schema?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    private static final String URL2 = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
    public static final String URL = "jdbc:mysql://localhost:3306/sakila?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    // 驱动类型
    private static final String MYSQL5_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String MYSQL8_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    // 用户名密码
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";

    // 生成器配置

    // 输出文件根目录
    public static final String OUTPUT_ROOT_DIR = "D://Temp";
    // 生成文件所在项目路径
    private static final String baseProjectPath = "C:\\Users\\xx\\Desktop\\demo";
    // 基本包名
    private static final String basePackage = "com.lancoo.schoolexcellentcourse";
    // 作者
    private static final String AUTHOR_NAME = "Lancoo";
    // table前缀
    private static final String[] tablePrefixToBeIgnored = {""};
    // 要生成的表名
    private static final String[] tableNamesToBeGenerated = {
            "resource_auth",
            "resource_base",
            "resource_cache",
            "resource_cdn",
            "resource_database",
            "resource_disk",
            "resource_dms",
            "resource_dns",
            "resource_ecs",
            "resource_eip",
            "resource_elb",
            "resource_im",
            "resource_mq",
            "resource_obs",
            "resource_rtc",
            "resource_sms",
            "resource_ssl",
            "resource_video_live"};

    /**
     * 数据库信息从本地文件加载，三个字段：url、username、password
     */
    private static final String LOCAL_CONNECTION_PROPERTIES_FILE = "C:\\Users\\Von\\Desktop\\jdbc.properties";

    public static void main(String[] args) {

        // 数据源配置
        DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/cloud_resource_management?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=GMT%2B8", "root", "123456")
                .dbQuery(new MySqlQuery()) // 数据库查询
                .schema("mybatis-plus") // 数据库schema(部分数据库适用)
                .typeConvert(new MySqlTypeConvert()) // 数据库类型转换器 自定义数据库表字段类型转换【可选】
                .keyWordsHandler(new MySqlKeyWordsHandler());// 数据库关键字处理器
        FastAutoGenerator generator = FastAutoGenerator.create(dataSourceBuilder);
        generator.globalConfig(builder -> {
            builder.author(AUTHOR_NAME) // 设置作者
                   .enableSwagger() // 开启 swagger 模式
                   .fileOverride() // 覆盖文件，全局配置，可以针对单一类型的文件进行覆盖
                   .outputDir(OUTPUT_ROOT_DIR); // 指定输出根目录，即项目路径
        }).templateConfig(builder -> {
            // 模板配置：指定模板文件路径
            builder.disable(TemplateType.ENTITY)
                   .entity("/templates/entity.java")
                   .service("/templates/service.java")
                   .serviceImpl("/templates/serviceImpl.java")
                   .mapper("/templates/mapper.java")
                   .xml("/templates/mapper.xml")
                   .controller("/templates/controller.java")
                   .build();
        }).injectionConfig(builder -> {
            // 注入配置
            builder.beforeOutputFile((tableInfo, objectMap) -> {
                       System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                   }).customMap(Collections.singletonMap("test", "baomidou"))
                   .customFile(Collections.singletonMap("test.txt", "/templates/test.vm")).build();
        }).packageConfig(builder -> {
            // 包配置：需要自定义
            // 项目根目录 + 父包名 + 模块名 + 具体的目录名即为真实的文件路径
            // TODO 根据需要设置
            builder.parent("com.lancoo.cloudresource") // 设置父包名
                   .moduleName("") // 设置父包模块名
                   .entity("entity") // 实体类所在文件夹名
                   .service("service") // service接口所在文件夹名
                   .serviceImpl("service.impl") // service实现类所在文件夹名
                   .mapper("mapper")  // dao层的mapper接口目录
                   .xml("mapper")
                   .controller("controller")
                   // pathInfo用于指定绝对路径
                   // .pathInfo(Collections.singletonMap(OutputFile.xml, "D://Temp"))// 设置mapperXml生成路径
                   .build();
        }).strategyConfig(builder -> {
            // 策略配置
            builder.addInclude(tableNamesToBeGenerated); // 设置需要生成的表名
            builder.addTablePrefix(tablePrefixToBeIgnored); // 设置过滤表前缀
            // 实体类输出配置
            Entity.Builder entityBuilder = builder.entityBuilder();
            //.superClass(BaseEntity.class)
            // TODO 是否生成序列化ID
            // entityBuilder.disableSerialVersionUUID(); // 禁用生成serialVersionUUID
            //.enableChainModel()
            entityBuilder.enableLombok();
            entityBuilder.fileOverride();
            //.enableRemoveIsPrefix()
            //.enableTableFieldAnnotation()
            //.enableActiveRecord()
            //.versionColumnName("version")
            //.versionPropertyName("version")
            //.logicDeleteColumnName("deleted")
            //.logicDeletePropertyName("deleteFlag")
            //.naming(NamingStrategy.no_change)
            //.columnNaming(NamingStrategy.underline_to_camel)
            //.addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time") //
            //.addIgnoreColumns("age") // 忽略的列
            //.addTableFills(new Column("create_time", FieldFill.INSERT))
            //.addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE)).idType(IdType.AUTO)
            // .formatFileName("%sEntity") // 格式化命名

            // Mapper XML配置
            Mapper.Builder mapperBuilder = builder.mapperBuilder();
            mapperBuilder.fileOverride(); // 是否覆盖之前的mapper.xml文件
            mapperBuilder.enableBaseColumnList(); // 添加字段列表，即sql标签

            // TODO 默认生成增删改查的XML标签
            // mapperBuilder.enableBaseCrudTag(); // 生成默认的crud标签
            mapperBuilder.enableBaseResultMap(); // 添加实体类和数据库字段映射，即ResultMap标签

            Controller.Builder controllerBuilder = builder.controllerBuilder();
            controllerBuilder.enableRestStyle();
            controllerBuilder.enableHyphenStyle();
            controllerBuilder.fileOverride();
            builder.build();
        });
        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        generator.templateEngine(new FreemarkerTemplateEngine());
        // 执行生成行为
        generator.execute();
    }
}
