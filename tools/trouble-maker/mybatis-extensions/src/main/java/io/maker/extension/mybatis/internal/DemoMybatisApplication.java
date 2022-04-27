package io.maker.extension.mybatis.internal;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 生成基础增加删除修改查询功能 （按照generatorConfig.xml生成）
 **/
public class DemoMybatisApplication {

	public void generator() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		// 指定配置文件
		File configFile = new File("C:\\Users\\vonline\\Desktop\\code-samples\\tools\\trouble-maker\\mybatis-extensions\\build\\resources\\main\\mybatis\\generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	// 执行main方法以生成代码
	public static void main(String[] args) {
		List<String> asList = Arrays.asList(", B".split(","));
		System.out.println(asList);
		
//		try {
//			DemoMybatisApplication generatorSqlmap = new DemoMybatisApplication();
//			generatorSqlmap.generator();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
