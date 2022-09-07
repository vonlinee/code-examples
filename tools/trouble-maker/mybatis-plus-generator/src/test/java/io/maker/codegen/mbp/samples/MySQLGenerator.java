package io.maker.codegen.mbp.samples;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import io.maker.base.utils.PropertiesUtils;
import org.springframework.util.StopWatch;

import java.util.Collections;
import java.util.Properties;

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
    private static final String[] tableNamesToBeGenerated = {"tch_age_range_report",
            "tch_baseinfo_report",
            "tch_edu_report",
            "tch_teach_info_report",
            "tch_title_report"};

    /**
     * 数据库信息从本地文件加载，三个字段：url、username、password
     */
    private static final String LOCAL_CONNECTION_PROPERTIES_FILE = "C:\\Users\\Administrator\\Desktop\\jdbc.properties";

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch("代码生成");
        stopWatch.start("数据源配置");
        System.out.println("开始配置");
        Properties properties = PropertiesUtils.loadProperties(LOCAL_CONNECTION_PROPERTIES_FILE);

        // 数据源配置
        DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig.Builder(properties)
                .dbQuery(new MySqlQuery()) // 数据库查询
                .schema("mybatis-plus") // 数据库schema(部分数据库适用)
                .typeConvert(new MySqlTypeConvert()) // 数据库类型转换器 自定义数据库表字段类型转换【可选】
                .keyWordsHandler(new MySqlKeyWordsHandler());// 数据库关键字处理器
        stopWatch.stop();
        stopWatch.start("生成配置");
        FastAutoGenerator generator = FastAutoGenerator.create(dataSourceBuilder);
        generator.globalConfig(builder -> {
            builder.author(AUTHOR_NAME) // 设置作者
                    // .enableSwagger() // 开启 swagger 模式
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
            builder.parent("org.lancoo.educenter") // 设置父包名
                    .moduleName("") // 设置父包模块名
                    .entity("entities") // 实体类所在文件夹名
                    .service("service") // service接口所在文件夹名
                    .serviceImpl("service.impl") // service实现类所在文件夹名
                    .mapper("mapper")  // dao层的mapper接口目录
                    .xml("mapper")
                    .controller("controller")
                    .other("other")
                    // pathInfo用于指定绝对路径
                    .pathInfo(Collections.singletonMap(OutputFile.xml, "D://Temp"))// 设置mapperXml生成路径
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
        stopWatch.stop();
        stopWatch.start("代码生成");
        // 执行生成行为
        generator.execute();
        stopWatch.stop();
        System.out.print(stopWatch.prettyPrint());
    }
}
