package io.maker.generator.mbp.samples;

import java.util.Collections;

import io.maker.generator.mbp.FastAutoGenerator;
import io.maker.generator.mbp.config.OutputFile;
import io.maker.generator.mbp.engine.FreemarkerTemplateEngine;

/**
 * https://github.com/baomidou/generator
 * 
 */
public class MySQLGenerator {

	public static final String url = "jdbc:mysql://localhost:3306/mysql_learn?createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";

	public static void main(String[] args) {

		// DataSourceConfig dataSourceConfig = new
		// DataSourceConfig.Builder("jdbc:mysql://localhost:3306/business?useUnicode=true&characterEncoding=utf8",
		// "root", "123456").build();

		FastAutoGenerator.create(url, "root", "123456").globalConfig(builder -> {
			builder.author("baomidou") // 设置作者
					.enableSwagger() // 开启 swagger 模式
					.fileOverride() // 覆盖已生成文件
					.outputDir("D://"); // 指定输出目录
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
