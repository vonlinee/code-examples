package io.maker.codegen.mbp.samples;

import java.util.Collections;

import io.maker.codegen.mbp.FastAutoGenerator;
import io.maker.codegen.mbp.config.DataSourceConfig;
import io.maker.codegen.mbp.config.OutputFile;
import io.maker.codegen.mbp.config.TemplateType;
import io.maker.codegen.mbp.config.converts.MySqlTypeConvert;
import io.maker.codegen.mbp.config.querys.MySqlQuery;
import io.maker.codegen.mbp.engine.FreemarkerTemplateEngine;
import io.maker.codegen.mbp.keywords.MySqlKeyWordsHandler;

/**
 * https://github.com/baomidou/generator
 */
public class MySQLGenerator {

	public static final String URL1 = "jdbc:mysql://localhost:3306/information_schema?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";

	public static final String OUTPUT_ROOT_DIR = "D://Temp";

	// 数据库配置四要素
	private static final String MYSQL5_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String MYSQL8_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private static final String URL2 = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
	private static final String username = "root";
	private static final String password = "123456";

	// 生成文件所在项目路径
	private static String baseProjectPath = "C:\\Users\\xx\\Desktop\\demo";
	// 基本包名
	private static String basePackage = "com.example";
	// 作者
	private static String authorName = "xx";
	// table前缀
	private static String[] prefix = {"t_"};
	// 要生成的表名
	private static String[] tables = { "t_orc_ec_miss_sale_order" };

	public static void main(String[] args) {
		// 数据源配置
		DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig.Builder(
				"jdbc:mysql://172.26.136.195:3306/orc?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false",
				"appuser", "app@user!!")
				.dbQuery(new MySqlQuery()) // 数据库查询
				.schema("mybatis-plus") // 数据库schema(部分数据库适用)
				.typeConvert(new MySqlTypeConvert()) // 数据库类型转换器 自定义数据库表字段类型转换【可选】
				.keyWordsHandler(new MySqlKeyWordsHandler());// 数据库关键字处理器

		FastAutoGenerator generator = FastAutoGenerator.create(dataSourceBuilder);

		generator.globalConfig(builder -> {
			builder.author("baomidou") // 设置作者
					.enableSwagger() // 开启 swagger 模式
					.fileOverride() // 覆盖文件
					.outputDir(OUTPUT_ROOT_DIR); // 指定输出目录
		}).templateConfig(builder -> {
			// 模板配置
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
			// 包配置
			builder.parent("") // 设置父包名
					.moduleName("com.ly.adp.orc") // 设置父包模块名
					.entity("entities")
					.service("service")
					.serviceImpl("service.impl")
					.mapper("dao.mapper")
					.xml("mybatis.mapping")
					.controller("controller")
					.other("other")
					.pathInfo(Collections.singletonMap(OutputFile.xml, "D://Temp"))// 设置mapperXml生成路径
					.build();
		}).strategyConfig(builder -> {
			// 策略配置
			builder.addInclude(tables) // 设置需要生成的表名
					.addTablePrefix(prefix); // 设置过滤表前缀
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
					.enableBaseCrudTag(); 
		}).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}
