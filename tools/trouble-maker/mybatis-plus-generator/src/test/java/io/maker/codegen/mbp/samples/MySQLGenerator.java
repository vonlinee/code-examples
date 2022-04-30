package io.maker.codegen.mbp.samples;

import java.util.Collections;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import io.maker.codegen.mbp.FastAutoGenerator;
import io.maker.codegen.mbp.config.DataSourceConfig;
import io.maker.codegen.mbp.config.InjectionConfig;
import io.maker.codegen.mbp.config.OutputFile;
import io.maker.codegen.mbp.config.PackageConfig;
import io.maker.codegen.mbp.config.StrategyConfig;
import io.maker.codegen.mbp.config.TemplateConfig;
import io.maker.codegen.mbp.config.TemplateType;
import io.maker.codegen.mbp.config.converts.MySqlTypeConvert;
import io.maker.codegen.mbp.config.querys.MySqlQuery;
import io.maker.codegen.mbp.config.rules.NamingStrategy;
import io.maker.codegen.mbp.engine.FreemarkerTemplateEngine;
import io.maker.codegen.mbp.entity.BaseEntity;
import io.maker.codegen.mbp.fill.Column;
import io.maker.codegen.mbp.fill.Property;
import io.maker.codegen.mbp.keywords.MySqlKeyWordsHandler;

/**
 * https://github.com/baomidou/generator
 */
public class MySQLGenerator {

	public static final String url = "jdbc:mysql://localhost:3306/information_schema?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";

	public static final String OUTPUT_ROOT_DIR = "D://Temp";

	// 数据库配置四要素
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String url1 = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
	private static String username = "root";
	private static String password = "root";

	// 生成文件所在项目路径
	private static String baseProjectPath = "C:\\Users\\xx\\Desktop\\demo";
	// 基本包名
	private static String basePackage = "com.example";
	// 作者
	private static String authorName = "xx";
	// table前缀
	// private static String prefix="t_";
	// 要生成的表名
	private static String[] tables = { "application" };

	public static void main(String[] args) {
		// 数据源配置
		DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus",
				"root", "123456").dbQuery(new MySqlQuery()) // 数据库查询
				.schema("mybatis-plus") // 数据库schema(部分数据库适用)
				.typeConvert(new MySqlTypeConvert()) // 数据库类型转换器 自定义数据库表字段类型转换【可选】
				.keyWordsHandler(new MySqlKeyWordsHandler()) // 数据库关键字处理器
				.build();

		FastAutoGenerator.create(url, "root", "123456").globalConfig(builder -> {
			builder.author("baomidou") // 设置作者
					.enableSwagger() // 开启 swagger 模式
					.outputDir(OUTPUT_ROOT_DIR); // 指定输出目录
		}).templateConfig(builder -> {
			// 模板配置
			builder.disable(TemplateType.ENTITY).entity("/templates/entity.java").service("/templates/service.java")
					.serviceImpl("/templates/serviceImpl.java").mapper("/templates/mapper.java")
					.xml("/templates/mapper.xml").controller("/templates/controller.java").build();
		}).injectionConfig(builder -> {
			builder.beforeOutputFile((tableInfo, objectMap) -> {
				System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
			}).customMap(Collections.singletonMap("test", "baomidou"))
					.customFile(Collections.singletonMap("test.txt", "/templates/test.vm")).build();
		}).packageConfig(builder -> {

			builder.parent("com.baomidou.mybatisplus.samples.generator").moduleName("sys").entity("po")
					.service("service").serviceImpl("service.impl").mapper("mapper").xml("mapper.xml")
					.controller("controller").other("other").pathInfo(Collections.singletonMap(OutputFile.xml, "D://"))
					.build();

			builder.parent("generator") // 设置父包名
					.moduleName("system") // 设置父包模块名
					.pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
		}).strategyConfig(builder -> {
			builder.addInclude("COLUMNS", "TABLES") // 设置需要生成的表名
					.addTablePrefix("t_", "c_"); // 设置过滤表前缀
			// 实体类输出配置
			builder.entityBuilder().superClass(BaseEntity.class).disableSerialVersionUUID() // 禁用生成serialVersionUUID
					.enableChainModel().enableLombok().enableRemoveIsPrefix().enableTableFieldAnnotation()
					.enableActiveRecord().versionColumnName("version").versionPropertyName("version")
					.logicDeleteColumnName("deleted").logicDeletePropertyName("deleteFlag")
					.naming(NamingStrategy.no_change).columnNaming(NamingStrategy.underline_to_camel)
					.addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
					.addIgnoreColumns("age").addTableFills(new Column("create_time", FieldFill.INSERT))
					.addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE)).idType(IdType.AUTO)
					.formatFileName("%sEntity").build();
			// Mapper XML配置
			builder.mapperBuilder().fileOverride() // 是否覆盖之前的mapper.xml文件
					.enableBaseColumnList() // 添加字段列表，即sql标签
					.enableBaseResultMap(); // 添加实体类和数据库字段映射，即ResultMap标签
		}).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}
