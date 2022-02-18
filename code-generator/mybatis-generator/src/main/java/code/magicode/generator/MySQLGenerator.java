package code.magicode.generator;

import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MySQLGenerator {

	public static void main(String[] args) {

		FastAutoGenerator generator = FastAutoGenerator.create(
				"jdbc:mysql://172.26.136.195:3306/csc?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8",
				"appuser", "app@user!!").globalConfig(builder -> {
					builder.author("ly-busicen") // 设置作者
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.outputDir("D://Temp"); // 指定输出目录
				}).packageConfig(builder -> {
					builder.parent("mybatis") // 设置父包名
							.moduleName("") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://Temp")); // 设置mapperXml生成路径
				}).strategyConfig(builder -> {
					builder.addInclude("t_sac_onetask_receive_object") // 设置需要生成的表名
							.addTablePrefix("") // 设置过滤表前缀
							.mapperBuilder()
							.enableBaseColumnList()
							.enableBaseResultMap();
				}).templateEngine(new FreemarkerTemplateEngine()); // 使用Freemarker引擎模板，默认的是Velocity引擎模板
		generator.execute();
		
	}
}
