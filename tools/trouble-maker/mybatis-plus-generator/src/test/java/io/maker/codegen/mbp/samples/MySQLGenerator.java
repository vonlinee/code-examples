package io.maker.codegen.mbp.samples;

import io.maker.codegen.mbp.FastAutoGenerator;
import io.maker.codegen.mbp.config.DataSourceConfig;
import io.maker.codegen.mbp.config.TemplateType;
import io.maker.codegen.mbp.config.converts.MySqlTypeConvert;
import io.maker.codegen.mbp.config.querys.MySqlQuery;
import io.maker.codegen.mbp.engine.FreemarkerTemplateEngine;
import io.maker.codegen.mbp.keywords.MySqlKeyWordsHandler;
import org.springframework.util.StopWatch;

import java.util.Collections;

/**
 * https://github.com/baomidou/generator
 */
public class MySQLGenerator {

    public static final String URL1 = "jdbc:mysql://localhost:3306/information_schema?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";

    public static final String OUTPUT_ROOT_DIR = "D://Temp";

    // 数据库连接信息配置
    private static final String MYSQL5_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String MYSQL8_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL2 = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
    public static final String URL = "jdbc:mysql://localhost:3306/sakila?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";

    // 生成文件所在项目路径
    private static String baseProjectPath = "C:\\Users\\xx\\Desktop\\demo";
    // 基本包名
    private static String basePackage = "com.example";
    // 作者
    private static final String AUTHOR_NAME = "xx";
    // table前缀
    private static final String[] tablePrefixToBeIgnored = {"t_"};
    // 要生成的表名
    private static final String[] tableNamesToBeGenerated = {"film"};

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch("代码生成");

        stopWatch.start("数据源配置");
        // 数据源配置
        DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig.Builder(URL, USER_NAME, PASSWORD)
                .dbQuery(new MySqlQuery()) // 数据库查询
                .schema("mybatis-plus") // 数据库schema(部分数据库适用)
                .typeConvert(new MySqlTypeConvert()) // 数据库类型转换器 自定义数据库表字段类型转换【可选】
                .keyWordsHandler(new MySqlKeyWordsHandler());// 数据库关键字处理器
        stopWatch.stop();
        stopWatch.start("生成配置");
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
                    })
                    .customMap(Collections.singletonMap("test", "baomidou"))
                    .customFile(Collections.singletonMap("test.txt", "/templates/test.vm")).build();
        }).packageConfig(builder -> {
            // 包配置：需要自定义
            // 项目根目录 + 父包名 + 模块名 + 具体的目录名即为真实的文件路径
            builder.parent("") // 设置父包名
                    .moduleName("mybatis") // 设置父包模块名
                    .entity("entities") // 实体类所在文件夹名
                    .service("service") // service接口所在文件夹名
                    .serviceImpl("service.impl") // service实现类所在文件夹名
                    .mapper("dao.mapper")  // dao层的mapper接口目录
                    .xml("mybatis.mapping")
                    .controller("controller")
                    .other("other")
                    // pathInfo用于指定绝对路径
                    // .pathInfo(Collections.singletonMap(OutputFile.xml, "D://Temp"))// 设置mapperXml生成路径
                    .build();
        }).strategyConfig(builder -> {
            // 策略配置
            builder.addInclude(tableNamesToBeGenerated); // 设置需要生成的表名
            builder.addTablePrefix(tablePrefixToBeIgnored); // 设置过滤表前缀
            // 实体类输出配置
            builder.entityBuilder()
                    //.superClass(BaseEntity.class)
                    .disableSerialVersionUUID() // 禁用生成serialVersionUUID
                    //.enableChainModel()
                    //.enableLombok()
                    .fileOverride()
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
                    .build();
            // Mapper XML配置
            builder.mapperBuilder()
                    .fileOverride() // 是否覆盖之前的mapper.xml文件
                    .enableBaseColumnList() // 添加字段列表，即sql标签
                    .enableBaseResultMap() // 添加实体类和数据库字段映射，即ResultMap标签
                    .enableBaseCrudTag(); // 生成默认的crud标签
        }).templateEngine(new FreemarkerTemplateEngine()); // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        stopWatch.stop();

        stopWatch.start("代码生成");
        // 执行生成行为
        generator.execute();
        stopWatch.stop();
        System.out.print(stopWatch.prettyPrint());
    }
}
